package com.fatec.scireclass.repository;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Endereco;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String>{

    Endereco findByUsuario(Usuario usuario);

    Endereco findByCurso(Curso curso);
}
