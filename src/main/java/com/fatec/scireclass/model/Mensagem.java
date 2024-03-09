package com.fatec.scireclass.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Mensagem {
    @Id
    private String id;
    private String mensagens;
    private Instant instante;
    @DBRef
    private Chat chat;
    @DBRef
    private Usuario usuario;

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
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Chat getChat() {
        return chat;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }
    
}
