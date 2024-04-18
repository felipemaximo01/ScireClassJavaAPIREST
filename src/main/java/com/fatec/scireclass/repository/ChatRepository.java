package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Chat;
import com.fatec.scireclass.model.Usuario;

import java.util.List;


@Repository
public interface ChatRepository extends  MongoRepository<Chat,String> {

    List<Chat> findByAlunoAndProfessor(Usuario aluno, Usuario professor);
    
}
