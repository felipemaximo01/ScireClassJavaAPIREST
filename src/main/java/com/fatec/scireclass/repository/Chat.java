package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Chat extends  MongoRepository<Chat,String> {
    
}
