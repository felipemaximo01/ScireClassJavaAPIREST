package com.fatec.scireclass.model.dto;

import java.time.Instant;

public class ChatDTO {

    private String id;

    private Instant dtUltimaMensagem;

    private String usuario;

    private String ultimaMensagem;

    private String cursoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String usuarioId;

    public Instant getDtUltimaMensagem() {
        return dtUltimaMensagem;
    }

    public void setDtUltimaMensagem(Instant dtUltimaMensagem) {
        this.dtUltimaMensagem = dtUltimaMensagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    public void setUltimaMensagem(String ultimaMensagem) {
        this.ultimaMensagem = ultimaMensagem;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}