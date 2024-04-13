package com.fatec.scireclass.service.eventlistener.cadastro;

import com.fatec.scireclass.model.Usuario;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class CadastroEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private transient Usuario usuario;

    public CadastroEvent(
            Usuario usuario, Locale locale, String appUrl) {
        super(usuario);

        this.usuario = usuario;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
