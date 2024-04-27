package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.scireclass.model.Aula;

public interface AulaRepository extends MongoRepository<Aula, String>{
    
}
