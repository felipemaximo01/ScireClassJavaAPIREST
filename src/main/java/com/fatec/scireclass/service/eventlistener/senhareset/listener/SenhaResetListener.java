package com.fatec.scireclass.service.eventlistener.senhareset.listener;


import com.fatec.scireclass.service.eventlistener.senhareset.SenhaResetEvent;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.service.implementation.EmailSenderService;
import com.fatec.scireclass.service.TokenSenhaResetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SenhaResetListener implements ApplicationListener<SenhaResetEvent> {

    @Autowired
    private TokenSenhaResetService tokenSenhaResetService;
    @Autowired
    private EmailSenderService emailSenderService;
    
    @Override
    public void onApplicationEvent(SenhaResetEvent event) { this.resetSenha(event); }

    private void resetSenha(SenhaResetEvent event){
        Usuario usuario = event.getUsuario();
        String token = UUID.randomUUID().toString();
        tokenSenhaResetService.criarTokenSenha(usuario,token);

        String resetUrl = "http://localhost:3000/usuario/mudarSenha/"+token;
        emailSenderService.sendEmail(usuario.getEmail(),"Alterar Senha",resetUrl);
    }
}
