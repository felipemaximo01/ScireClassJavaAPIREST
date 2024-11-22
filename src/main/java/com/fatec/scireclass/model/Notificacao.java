package com.fatec.scireclass.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notificacao {
    private String mensagem;
    private String cursoId;
    private String professorId;

    public Notificacao() {}

    @JsonCreator
    public Notificacao(@JsonProperty("mensagem") String mensagem,
                       @JsonProperty("cursoId") String cursoId,
                       @JsonProperty("professorId") String professorId) {
        this.mensagem = mensagem;
        this.cursoId = cursoId;
        this.professorId = professorId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
}
