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
    private List<Usuario> usuarios;
    @DBRef
    private List<Mensagem> mensagens;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public List<Mensagem> getMensagens() {
        return mensagens;
    }
    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    
}
