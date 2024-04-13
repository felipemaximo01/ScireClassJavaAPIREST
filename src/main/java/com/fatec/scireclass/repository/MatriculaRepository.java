package com.fatec.scireclass.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Matricula;

@Repository
public interface MatriculaRepository extends MongoRepository<Matricula,String> {
    
    List<Matricula> findAllById(String id);
    
    Matricula getById(String id);

    Integer countAllBy();

    Matricula findMatriculaByAlunoAndCurso(String alunoId, String cursoId);

    List<Matricula> findAllByCurso_Criador_Id(String usuarioId);
    List<Matricula> findAllByCurso_Criador_IdAndCursoId(String usuarioId,String cursoId);

    Boolean existsByAluno_IdAndCursoId(String usuarioId, String cursoId);

    List<Matricula> findAllByAluno_Id(String alunoId);
    
}
