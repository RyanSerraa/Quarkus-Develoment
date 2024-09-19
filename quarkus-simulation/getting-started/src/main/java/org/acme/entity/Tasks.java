package org.acme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="userquarkustarefa")
public class Tasks {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="titulo", nullable=false)
    private String titulo;
    @Column(name="descricao", nullable=false)
    private String descricao;
    @Column(name="status", nullable=false)
    private String status;
    @Column(name="prioridade", nullable=false)
    private String prioridade;
    @ManyToOne
    @JoinColumn(name="id_usuario", referencedColumnName = "id")
    private Usuario id_usuario;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Usuario getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }
}
