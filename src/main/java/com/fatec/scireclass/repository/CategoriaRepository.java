package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Categoria;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria,String> {
    
}
