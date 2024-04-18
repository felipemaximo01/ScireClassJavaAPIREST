package com.fatec.scireclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.dto.ChatDTO;
import com.fatec.scireclass.model.dto.MensagemDTO;
import com.fatec.scireclass.model.mapper.ChatMapper;
import com.fatec.scireclass.service.ChatService;
import com.fatec.scireclass.service.MensagemService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class ChatMessageController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/createChat/{alunoID}/{professorID}")
    public ResponseEntity<ChatDTO> createChat(@PathVariable String alunoID, @PathVariable String professorID){
        return new ResponseEntity<>(ChatMapper.ChatToChatDTO(chatService.createChat(alunoID, professorID)), HttpStatus.OK);
    }

    @PostMapping("/send/{chatID}/{userSend}")
    public ResponseEntity<MensagemDTO> recebeMensagem(@RequestBody MensagemDTO mensagemDTO, @PathVariable String chatID){
        return null;
    }
}
