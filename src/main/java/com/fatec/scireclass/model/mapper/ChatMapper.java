package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.dto.ChatDTO;

public class ChatMapper {

    private ChatMapper(){
        throw new IllegalStateException("Utility class");
    }


    public static ChatDTO ChatToChatDTO(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        if(chat.getId() != null)
            chatDTO.setId(chat.getId());
        if(chat.getDtUltimaMensagem() != null)
            chatDTO.setDtUltimaMensagem(chat.getDtUltimaMensagem());
        if(chat.getUltimaMensagem() != null)
            chatDTO.setUltimaMensagem(chat.getUltimaMensagem());

        return chatDTO;
    }
    
}
