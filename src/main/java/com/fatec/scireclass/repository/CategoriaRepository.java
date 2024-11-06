package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Categoria;

import java.util.List;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria,String> {
    Categoria findCategoriaById(String id);

    Categoria findCategoriaByNome(String nome);

    List<Categoria> findAll();
}
