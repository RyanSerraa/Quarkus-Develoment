package org.acme.test;

import java.util.List;

import org.acme.entity.Usuario;
import org.acme.repository.UserRepository;
import org.acme.service.TasksService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;

@QuarkusTest
public class TaskTest {
    public final List<String> status = List.of("pendente", "em progresso", "concluída");
    public final List<String> prioridade = List.of("alta", "média", "baixa");
    
        UserRepository repository= new UserRepository();
        long userId;
        long userId2;
        Usuario usuario = new Usuario();
        Usuario usuario2 = new Usuario();
    @BeforeEach
@Transactional
public void createUser() {
    usuario.setEmail("rserra@gmail.com");
    usuario.setPassword("wl45");
    usuario.setUsername("Ryan");

    if (repository.find("email", usuario.getEmail()).firstResult() == null) {
        repository.persist(usuario);
        // Ensure the ID is set after persistence
        userId = usuario.getId();
    }

    usuario2.setEmail("rserra2@gmail.com");
    usuario2.setPassword("wl45");
    usuario2.setUsername("Ryan");

    if (repository.find("email", usuario2.getEmail()).firstResult() == null) {
        repository.persist(usuario2);
        // Ensure the ID is set after persistence
        userId2 = usuario2.getId();
    }
}

    @Test
    public void testTaskCreate(){
            var createTask = new TasksService();
            createTask.setPrioridade(prioridade.get(1));
            createTask.setDescricao("faça um endpoint ");
            createTask.setTitulo("é muito dificil");
            createTask.setStatus(status.get(2));
            var response=given()
            .contentType(ContentType.JSON)
            .when()
            .body(createTask)
            .pathParam("usuarioId", userId)
            .when()
            .post("/usuarios/{usuarioId}/tasks")
            .then()
            .extract()
            .response();
            System.out.println(createTask.getDescricao() + " "+createTask.getPrioridade()+" "+createTask.getStatus()+" "+createTask.getTitulo());
            System.out.println("Usuario:" +"\n"+ usuario.getEmail()+"\n"+usuario.getPassword()+"\n"+usuario.getUsername());
            assertEquals(201, response.statusCode());
        
            assertEquals(createTask.getDescricao() , response.jsonPath().getString("descricao"));
            assertEquals(createTask.getPrioridade() , response.jsonPath().getString("prioridade"));
            assertEquals(createTask.getStatus(), response.jsonPath().getString("status"));
            assertEquals(createTask.getTitulo() , response.jsonPath().getString("titulo"));
    }
    @Test
    public void testTaskReturn() {
        var createTask = new TasksService();
        createTask.setPrioridade(prioridade.get(1));
        createTask.setDescricao("Faça um Front-end");
        createTask.setTitulo("é muito facil");
        createTask.setStatus(status.get(0));
    
        // Criando a tarefa
        var response = given()
            .contentType(ContentType.JSON)
            .when()
            .body(createTask)
            .pathParam("usuarioId", userId2)
            .post("/usuarios/{usuarioId}/tasks")
            .then()
            .extract()
            .response();
    
        // Verifica o status da criação da tarefa
        assertEquals(201, response.statusCode(), "Falha ao criar a tarefa");
    
        // Recuperando a tarefa criada
        var responseGet = given()
        .get("/usuarios/1/tasks")
        .then()
        .extract()
        .response();

assertEquals(200, responseGet.statusCode(), "Falha ao recuperar as tarefas");
    
        // Verifica se a requisição GET foi bem-sucedida
        assertEquals(200, responseGet.statusCode(), "Falha ao recuperar as tarefas");
    
        // Verifica o conteúdo da tarefa
        System.out.println("Tarefa criada: " + createTask.getDescricao() + " " + createTask.getPrioridade() + " " + createTask.getStatus() + " " + createTask.getTitulo());
        System.out.println("Usuario: " + "\n" + usuario.getEmail() + "\n" + usuario.getPassword() + "\n" + usuario.getUsername());
    }
    
    @Test
    public void testTaskDelete() {
        var createTask = new TasksService();
        createTask.setPrioridade(prioridade.get(1));
        createTask.setDescricao("Faça um Front-end");
        createTask.setTitulo("é muito facil");
        createTask.setStatus(status.get(0));
    
        // Criando a tarefa
        var response = given()
            .contentType(ContentType.JSON)
            .when()
            .body(createTask)
            .pathParam("usuarioId", userId2)
            .post("/usuarios/{usuarioId}/tasks")
            .then()
            .extract()
            .response();
    
        // Verifica o status da criação da tarefa
        assertEquals(201, response.statusCode(), "Falha ao criar a tarefa");
    
        // Get the task ID from the response to delete later
        Long taskId = response.jsonPath().getLong("id"); // Adjust the path according to your API response
    
        // Deletando a tarefa
        var responseDelete = given()
            .pathParam("usuarioId", userId2)
            .pathParam("taskId", taskId) // Assuming you need a task ID to delete
            .delete("/usuarios/{usuarioId}/tasks/{taskId}") // Adjust your endpoint
            .then()
            .extract()
            .response();
    
        assertEquals(204, responseDelete.statusCode(), "Falha ao deletar a tarefa");
    }
    
}
