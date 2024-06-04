package com.fatec.scireclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatec.scireclass.model.dto.ChatDTO;
import com.fatec.scireclass.model.dto.MensagemDTO;
import com.fatec.scireclass.model.mapper.ChatMapper;
import com.fatec.scireclass.model.mapper.MensagemMapper;
import com.fatec.scireclass.service.ChatService;
import com.fatec.scireclass.service.MensagemService;


@RestController
@RequestMapping("/chat")
public class ChatMessageController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private MensagemService mensagemService;

    @PostMapping("/createChat/{alunoID}/{professorID}/{cursoID}")
    public ResponseEntity<ChatDTO> createChat(@PathVariable String alunoID, @PathVariable String professorID,@PathVariable String cursoID){
        return new ResponseEntity<>(ChatMapper.ChatToChatDTO(chatService.createChat(alunoID, professorID,cursoID)), HttpStatus.OK);
    }

    @PostMapping("/send/{chatID}/{usuarioID}")
    public ResponseEntity<MensagemDTO> sendMensagem(@RequestBody MensagemDTO mensagemDTO, @PathVariable String chatID, @PathVariable String usuarioID){
        return new ResponseEntity<>(MensagemMapper.mensagemToMensagemDTO(mensagemService.sendMensagem(mensagemDTO, chatID, usuarioID)), HttpStatus.OK);
    }

    @GetMapping("/getChats/{usuarioID}")
    public ResponseEntity<List<ChatDTO>> getChats(@PathVariable String usuarioID) {
        return new ResponseEntity<>(chatService.getChats(usuarioID),HttpStatus.OK);

    }

    @GetMapping("/getMessage/{chatID}/{usuarioID}")
    public ResponseEntity<List<MensagemDTO>> getMensagens(@PathVariable String chatID, @PathVariable String usuarioID) {
        return new ResponseEntity<>(mensagemService.getMensagens(chatID,usuarioID),HttpStatus.OK);
    }

    @GetMapping("/{chatID}/{usuarioID}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable String chatID, @PathVariable String usuarioID) {
        return new ResponseEntity<>(chatService.getChat(chatID, usuarioID),HttpStatus.OK);
    }

    @DeleteMapping("/deleteChat/{chatID}")
    public void deleteChat(@PathVariable String chatID){
        chatService.deleteChat(chatID);
    }
    
    
}
