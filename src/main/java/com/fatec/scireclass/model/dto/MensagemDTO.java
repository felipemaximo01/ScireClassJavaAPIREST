package com.fatec.scireclass.model.dto;

import java.time.Instant;

public class MensagemDTO {
    private String id;
    private String mensagens;
    private Instant instante;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMensagens() {
        return mensagens;
    }
    public void setMensagens(String mensagens) {
        this.mensagens = mensagens;
    }
    public Instant getInstante() {
        return instante;
    }
    public void setInstante(Instant instante) {
        this.instante = instante;
    }

    
}
