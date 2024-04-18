package com.fatec.scireclass.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Chat {
    @Id
    private String id;
    
    @DBRef
    private Usuario aluno;
    @DBRef
    private Usuario professor;
    @DBRef
    private List<Mensagem> mensagens;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Mensagem> getMensagens() {
        return mensagens;
    }
    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
    public Usuario getAluno() {
        return aluno;
    }
    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }
    public Usuario getProfessor() {
        return professor;
    }
    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }
    
    
}
