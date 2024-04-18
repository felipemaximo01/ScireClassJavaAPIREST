package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Mensagem;

@Service
public interface MensagemService {

    Mensagem saveMensagem(Mensagem mensagem);

    Mensagem getMensagem(String id);

    void deleteMensagem(String id);
    
} 
