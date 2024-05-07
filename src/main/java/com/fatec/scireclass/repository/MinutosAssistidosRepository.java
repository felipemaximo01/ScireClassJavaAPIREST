package com.fatec.scireclass.repository;

import com.fatec.scireclass.model.MinutosAssistidos;
import com.fatec.scireclass.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MinutosAssistidosRepository extends MongoRepository<MinutosAssistidos, String> {

    List<MinutosAssistidos> findByAlunoAndDataBetween(Usuario aluno, Date startOfDay, Date endOfDay);
}
