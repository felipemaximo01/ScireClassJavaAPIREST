package com.fatec.scireclass.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TokenVerificacao {
    private static final int EXPIRATION = 60 * 24;

    @Id
    private String  id;
    private String token;
    @DBRef
    private Usuario usuario;

    private Date expiracaoData;

    public TokenVerificacao() {
        expiracaoData = calculateExpiracaoData(EXPIRATION);
    }

    private Date calculateExpiracaoData(int minutos){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, minutos);
        return new Date(cal.getTime().getTime());
    }

    public static int getExpiration() {
        return EXPIRATION;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getExpiracaoData() {
        return expiracaoData;
    }

    public void setExpiracaoData(Date expiracaoData) {
        this.expiracaoData = expiracaoData;
    }

    
}
