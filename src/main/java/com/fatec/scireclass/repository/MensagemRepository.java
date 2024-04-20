package com.fatec.scireclass.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.Mensagem;

@Repository
public interface MensagemRepository extends MongoRepository<Mensagem,String> {

    List<Mensagem> findByChat(Chat chat);
    
}
