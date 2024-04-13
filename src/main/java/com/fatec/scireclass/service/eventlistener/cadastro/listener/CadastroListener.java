package com.fatec.scireclass.service.eventlistener.cadastro.listener;

import com.fatec.scireclass.service.eventlistener.cadastro.CadastroEvent;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.service.EmailSenderService;
import com.fatec.scireclass.service.TokenVerificacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CadastroListener implements ApplicationListener<CadastroEvent> {

    @Autowired
    private TokenVerificacaoService tokenVerificacaoService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(CadastroEvent event) {
        this.confirmaCadastro(event);
    }

    private void confirmaCadastro(CadastroEvent event){
        Usuario usuario = event.getUsuario();
        String token = UUID.randomUUID().toString();
        tokenVerificacaoService.criarTokenVerificacao(usuario,token);

        String confirmacaoUrl ="http://localhost:8080"+ event.getAppUrl() + "/usuario/confirmarCadastro?token="+token;
        emailSenderService.sendEmail(usuario.getEmail(),"Confirmar Cadastro",confirmacaoUrl);
    }

}
