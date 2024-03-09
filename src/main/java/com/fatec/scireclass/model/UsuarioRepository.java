package com.fatec.scireclass.model;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);

    Usuario findByEmailAndSenha(String email, String senha);

    Usuario findUsuarioByEmailAndSenha(String email,String senha);

    Usuario findUsuarioById(String id);

    Usuario findUsuarioByEmail(String email);
}
