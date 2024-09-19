package org.acme.response;

import org.acme.entity.Tasks;

public class TasksResponse {
    private String titulo;
    private String descricao;
    private String status;
    private String prioridade;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
    
    public static  TasksResponse fromEntity(Tasks tasks){
        TasksResponse response= new TasksResponse();
        response.setDescricao(tasks.getDescricao());
        response.setStatus(tasks.getStatus());
        response.setPrioridade(tasks.getPrioridade());
        response.setTitulo(tasks.getTitulo());
        return response;
    }
}
