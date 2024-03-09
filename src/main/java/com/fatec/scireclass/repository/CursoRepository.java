package com.fatec.scireclass.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.Curso;

@Repository
public interface CursoRepository extends MongoRepository<Curso,String> {
    
    List<Curso> findByNomeLikeIgnoreCase(String nome);

    List<Curso> findByDescricaoLikeIgnoreCase(String descricao);

    Curso findCursoById(String id);
    
    List<Curso> findByCategoria(Categoria categoria);

    List<Curso> findAllByCriador_Id(String id);

    void deleteCursoByCriador_Id(String id);
}
