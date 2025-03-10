package org.acme.resource;

import java.util.List;
import java.util.Set;

import org.acme.entity.Usuario;
import org.acme.repository.UserRepository;
import org.acme.service.UserService;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private UserRepository repository;
    private Validator validator;

    @Inject
        public UsuarioResource(UserRepository repository, Validator validator){
            this.repository=repository;
            this.validator= validator;
        }
        
    @POST
    @Transactional
    public Response createUser(UserService userService){
        Set<ConstraintViolation<UserService>> violations= validator.validate(userService);
        if(!violations.isEmpty()){
            return Response.status(422).entity(violations).build();
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(userService.getEmail());
        usuario.setUsername(userService.getUsername());
        usuario.setPassword(userService.getPassword());
        repository.persist(usuario);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllUsers() {
        // Use User.findAll().list() correctly to fetch all User entities
        PanacheQuery<Usuario> query = repository.findAll();  // Make sure to call findAll() correctly
        List<Usuario> users = query.list();  // Convert the PanacheQuery to a List
        return Response.ok(users).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        Usuario user = repository.findById(id);
        if (user != null) {
            try {
                repository.delete(user);
                return Response.status(Response.Status.NO_CONTENT).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    

    
}
