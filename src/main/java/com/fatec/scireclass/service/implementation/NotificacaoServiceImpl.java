package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.service.NotificacaoService;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
    @Autowired
    private PubSubTemplate pubSubTemplate;


    @Override
    public void enviarNotificacaoMatricula(String mensagem) {
        pubSubTemplate.publish("notificacoes-matriculas", mensagem);
    }
}
