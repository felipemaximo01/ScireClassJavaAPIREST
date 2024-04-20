package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.dto.ChatDTO;

public class ChatMapper {

    private ChatMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Chat ChatDTOToChat(ChatDTO chatDTO){
        Chat chat = new Chat();
        if(chatDTO.getId() != null)
            chat.setId(chatDTO.getId());
        if(chatDTO.getAluno() != null)
            chat.setAluno(UsuarioMapper.usuarioDTOToUsuario(chatDTO.getAluno()));
        if(chatDTO.getProfessor() != null)
            chat.setProfessor(UsuarioMapper.usuarioDTOToUsuario(chatDTO.getProfessor()));

        return chat;
    }

    public static ChatDTO ChatToChatDTO(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        if(chat.getId() != null)
            chatDTO.setId(chat.getId());
        if(chat.getAluno() != null)
            chatDTO.setAluno(UsuarioMapper.usuarioToUsuarioDTO(chat.getAluno()));
        if(chat.getProfessor() != null)
            chatDTO.setProfessor(UsuarioMapper.usuarioToUsuarioDTO(chat.getProfessor()));

        return chatDTO;
    }
    
}
