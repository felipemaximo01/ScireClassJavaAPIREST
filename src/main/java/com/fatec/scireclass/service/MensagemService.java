package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.dto.MensagemDTO;

@Service
public interface MensagemService {

    Mensagem saveMensagem(Mensagem mensagem);

    Mensagem getMensagem(String id);

    void deleteMensagem(String id);

    Mensagem sendMensagem(MensagemDTO mensagemDTO, String chatID, String usuarioID);
    
} 
