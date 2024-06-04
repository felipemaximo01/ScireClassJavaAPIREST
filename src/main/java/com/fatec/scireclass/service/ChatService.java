package com.fatec.scireclass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.dto.ChatDTO;

@Service
public interface ChatService {
    Chat saveChat(Chat chat);

    ChatDTO getChat(String id, String usuarioID);

    void deleteChat(String id);

    Chat createChat(String alunoID, String professorID,String cursoId);

    List<ChatDTO> getChats(String usuarioID);
}
