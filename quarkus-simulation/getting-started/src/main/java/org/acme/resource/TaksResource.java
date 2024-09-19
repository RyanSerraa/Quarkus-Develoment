package org.acme.resource;

import java.util.Set;

import org.acme.entity.Tasks;
import org.acme.entity.Usuario;
import org.acme.repository.TasksRepository;
import org.acme.repository.UserRepository;
import org.acme.service.TasksService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("usuario/{usuarioId}/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaksResource {
    TasksRepository repository;
    Validator validator;
    UserRepository userRepository;
    @Inject
    public TaksResource(TasksRepository repository, Validator validator, UserRepository userRepository){
        this.repository=repository;
        this.validator=validator;
        this.userRepository=userRepository;
    } 
    
    @POST
    @Transactional
    public Response createTasks(@PathParam("usuarioId") long userId, TasksService tasksService){
         Set<ConstraintViolation<TasksService>> violations= validator.validate(tasksService);
        if(!violations.isEmpty()){
            return Response.status(422).entity(violations).build();
        }
        Usuario usuario = userRepository.findById(userId);
        Tasks tasks= new Tasks();
        tasks.setDescricao(tasksService.getDescricao());
        tasks.setPrioridade(tasksService.getPrioridade());
        tasks.setStatus(tasksService.getStatus());
        tasks.setTitulo(tasksService.getTitulo());
        tasks.setId_usuario(usuario);
        repository.persist(tasks);
        return Response.status(Response.Status.CREATED).entity(tasks).build();
    }
}
