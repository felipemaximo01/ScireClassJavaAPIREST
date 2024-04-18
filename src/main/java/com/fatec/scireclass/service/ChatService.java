package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Chat;

@Service
public interface ChatService {
    Chat saveChat(Chat chat);

    Chat getChat(String id);

    void deleteChat(String id);

    Chat createChat(String alunoID, String professorID);
}
