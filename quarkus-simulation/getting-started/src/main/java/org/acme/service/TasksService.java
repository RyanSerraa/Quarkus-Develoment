package org.acme.service;

import jakarta.validation.constraints.NotBlank;

public class TasksService {
    @NotBlank
    private String titulo;
    private String descricao;
    @NotBlank
    private String status;
    @NotBlank
    private String prioridade;
    public TasksService() {
    }
    public TasksService(@NotBlank String titulo, String descricao, @NotBlank String status,
            @NotBlank String prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
    }
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


}
