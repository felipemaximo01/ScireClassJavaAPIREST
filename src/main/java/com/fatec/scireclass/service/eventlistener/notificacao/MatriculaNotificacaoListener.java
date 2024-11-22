package com.fatec.scireclass.service.eventlistener.notificacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatec.scireclass.model.Notificacao;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.FireBaseNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;

import javax.annotation.PostConstruct;

@Component
public class MatriculaNotificacaoListener {

    @Autowired
    private FireBaseNotification fireBaseNotification;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PubSubTemplate pubSubTemplate;

    @PostConstruct
    public void enviarNotificacaoParaProfessor() {
        pubSubTemplate.subscribe("notificacoes-matriculas-sub", (message) -> {
            try {
                String mensagemJson = message.getPubsubMessage().getData().toStringUtf8();

                ObjectMapper mapper = new ObjectMapper();
                Notificacao notificacao = mapper.readValue(mensagemJson, Notificacao.class);
                Usuario professor = usuarioRepository.findUsuarioById(notificacao.getProfessorId());
                String mensagem = notificacao.getMensagem();

                String fcmToken = professor.getFcmToken();
                String titulo = "Nova matrícula no seu curso";
                fireBaseNotification.enviarNotificacaoParaProfessor(fcmToken, titulo, mensagem);
                message.ack();
            } catch (Exception e) {
                e.printStackTrace();
                message.ack();
            }

        });
    }
}
