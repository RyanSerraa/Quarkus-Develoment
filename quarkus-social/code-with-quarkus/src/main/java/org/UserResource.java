package org;
import java.util.List;
import java.util.Set;

import org.acme.ResponseError;
import org.acme.User;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON) // Diz que vou estar consumindo uma info JSON
@Produces(MediaType.APPLICATION_JSON) //  Diz que vou enviar Json, produzir 
public class UserResource {
    private UserRepository repository;
    private Validator validator;
    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository=repository;
        this.validator= validator;
    }
    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations= validator.validate(userRequest);
        if (!violations.isEmpty()){
            ResponseError responseError= ResponseError.createFromValidation(violations);
            return Response.status(422).entity(responseError).build();
        }
        // Example logic: Return a 201 Created response
        User user = new User();
        user.setAge(userRequest.getAge());// Aqui estamos setando o valor para colunas 
        user.setName(userRequest.getName());
        repository.persist(user);// Aqui estamos persistindo os valores na entidade
    
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllUsers() {
        // Use User.findAll().list() correctly to fetch all User entities
        PanacheQuery<User> query = repository.findAll();  // Make sure to call findAll() correctly
        List<User> users = query.list();  // Convert the PanacheQuery to a List
        return Response.ok(users).build();
    }
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUsers(@PathParam("id") Long id) {
        User user = repository.findById(id);
        if (user == null) {
            // User not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O usuario não existe na base de dados")
                    .build();
        }
        
        try {
            repository.delete(user);
            // Successfully deleted
            return Response.ok("Usuario deletado com sucesso").build();
        } catch (Exception e) {
            // Handle potential exceptions during delete
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao tentar excluir o usuário.")
                    .build();
        }
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUsers(@PathParam("id") Long id, CreateUserRequest userRequest){
        User user =repository.findById(id);
        if (user == null) {
            // User not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O usuario não existe na base de dados")
                    .build();
        }
        try {
        user.setAge(userRequest.getAge());// Aqui estamos setando o valor para colunas 
        user.setName(userRequest.getName());
        return Response.ok().build();
    
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao tentar atualizar o usuário.")
                    .build();
        }
}
}
