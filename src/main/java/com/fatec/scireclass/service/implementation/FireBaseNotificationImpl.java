package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.service.FireBaseNotification;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Service
public class FireBaseNotificationImpl implements FireBaseNotification {
    @Override
    public void enviarNotificacaoParaProfessor(String fcmToken, String titulo, String conteudo) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(titulo) // Defina o título da notificação
                    .setBody(conteudo) // Defina o conteúdo da notificação
                    .build();


            System.out.println("Preparando para enviar notificação...");
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(fcmToken)
                    .setAndroidConfig(
                            AndroidConfig.builder()
                                    .setPriority(AndroidConfig.Priority.HIGH)
                                    .build()
                    )
                    .build();

            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println("Notificação enviada com sucesso: " + response);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
