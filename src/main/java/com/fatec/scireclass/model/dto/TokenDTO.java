package com.fatec.scireclass.model.dto;

import com.fatec.scireclass.model.enums.Perfil;

public class TokenDTO {
    private String token;
    private String id;
    private Perfil perfil;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
    
}
