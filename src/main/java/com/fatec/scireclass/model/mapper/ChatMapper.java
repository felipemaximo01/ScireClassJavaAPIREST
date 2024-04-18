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

        return chat;
    }

    public static ChatDTO ChatToChatDTO(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        if(chat.getId() != null)
            chatDTO.setId(chat.getId());

        return chatDTO;
    }
    
}
