package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Endereco;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String>{
    
}
