package com.fatec.scireclass.model.dto;

public class ChatDTO {

    private String id;

    private UsuarioDTO professor;

    private UsuarioDTO aluno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsuarioDTO getProfessor() {
        return professor;
    }

    public void setProfessor(UsuarioDTO professor) {
        this.professor = professor;
    }

    public UsuarioDTO getAluno() {
        return aluno;
    }

    public void setAluno(UsuarioDTO aluno) {
        this.aluno = aluno;
    }

    
}