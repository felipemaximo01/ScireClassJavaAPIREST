package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Comentario;

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario, String>  {
    
}
