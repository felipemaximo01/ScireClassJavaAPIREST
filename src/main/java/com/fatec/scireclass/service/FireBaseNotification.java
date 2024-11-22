package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

@Service
public interface FireBaseNotification {
    void enviarNotificacaoParaProfessor(String fcmToken, String titulo, String conteudo);
}
