package org.acme.resource;

import java.util.List;
import java.util.Set;

import org.acme.entity.Tasks;
import org.acme.entity.Usuario;
import org.acme.repository.TasksRepository;
import org.acme.repository.UserRepository;
import org.acme.response.TasksResponse;
import org.acme.service.TasksService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("usuarios/{usuarioId}/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TasksResource {
    public final List<String> status = List.of("pendente", "em progresso", "concluída");
    public final List<String> prioridade = List.of("alta", "média", "baixa");
    TasksRepository repository;
    Validator validator;
    UserRepository userRepository;
    @Inject
    public TasksResource(TasksRepository repository, Validator validator, UserRepository userRepository){
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
        if(!this.status.contains(tasksService.getStatus())){
            return Response.status(422).entity("Violação nos tipos de status existentes: pendente, em progresso, concluída ").build();
        }
        if(!this.prioridade.contains(tasksService.getPrioridade())){
            return Response.status(422).entity("Violação nos tipos de status existentes: alta, média, baixa ").build();
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
    @GET
    public Response listTasks(@PathParam("usuarioId") long userId){
        // Verifica se o usuário existe
        Usuario user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        // Filtra as tarefas pelo usuário
        PanacheQuery<Tasks> query = repository.find("id_usuario", user);
        List<Tasks> tasks = query.list();
        
        // Converte a lista de Tasks para TasksResponse
        var tasksResponseList = tasks.stream()
                                     .map(TasksResponse::fromEntity)
                                     .toList();
        
        return Response.ok(tasksResponseList).build();
    }
    

    @DELETE
    @Transactional
    public Response deleteTasks(@PathParam("usuarioId")long id, @QueryParam("id") long taskId){
        Usuario user=userRepository.findById(id);
        if(user!=null){
            try {
               repository.deleteById(taskId); 
               return Response.status(Response.Status.NO_CONTENT).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
