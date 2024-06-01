package com.fatec.scireclass.model.dto;

import java.time.Instant;

public class ChatDTO {

    private String id;

    private Instant dtUltimaMensagem;

    private String usuario;

    private String ultimaMensagem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}