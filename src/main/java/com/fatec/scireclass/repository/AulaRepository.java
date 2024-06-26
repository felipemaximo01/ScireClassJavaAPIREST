package com.fatec.scireclass.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.scireclass.model.Aula;
import com.fatec.scireclass.model.Curso;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends MongoRepository<Aula, String>{

    List<Aula> findByCurso(Curso curso);

    Aula findAulaById(String id);
    
}
