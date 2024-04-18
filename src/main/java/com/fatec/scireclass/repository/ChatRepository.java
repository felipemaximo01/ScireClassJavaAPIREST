package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Chat;

@Repository
public interface ChatRepository extends  MongoRepository<Chat,String> {
    
}
