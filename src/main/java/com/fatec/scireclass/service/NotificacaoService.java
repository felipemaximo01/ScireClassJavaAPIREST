package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

@Service
public interface NotificacaoService {

    public void enviarNotificacaoMatricula(String mensagem);
}
