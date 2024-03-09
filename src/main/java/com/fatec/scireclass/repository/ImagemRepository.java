package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Imagem;

@Repository
public interface ImagemRepository extends MongoRepository<Imagem, String> {
    
    Imagem findByCursoId(String cursoId);
}
