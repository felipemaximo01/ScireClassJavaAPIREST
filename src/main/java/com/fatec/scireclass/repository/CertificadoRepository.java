package com.fatec.scireclass.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Certificado;
import com.fatec.scireclass.model.Matricula;

@Repository
public interface CertificadoRepository extends MongoRepository<Certificado,String> {

    List<Matricula> findAllByMatricula_Aluno_Id(String alunoid);
    
}
