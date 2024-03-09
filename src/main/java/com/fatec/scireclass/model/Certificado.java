package com.fatec.scireclass.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Certificado {
    @Id
    private String id;
    @DBRef
    private Usuario usuario;
    @DBRef
    private Curso curso;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    
}
