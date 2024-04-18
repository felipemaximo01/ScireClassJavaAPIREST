package com.fatec.scireclass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.repository.MensagemRepository;

@Service
public class MensagemServiceImpl implements MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Override
    public Mensagem saveMensagem(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem getMensagem(String id) {
        return mensagemRepository.findById(id).get();
    }

    @Override
    public void deleteMensagem(String id){
        mensagemRepository.deleteById(id);
    }
    
}
