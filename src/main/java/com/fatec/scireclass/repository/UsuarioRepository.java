package com.fatec.scireclass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.Usuario;


@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);

    Usuario findByEmailAndSenha(String email, String senha);

    Usuario findUsuarioByEmailAndSenha(String email,String senha);

    Usuario findUsuarioById(String id);

    Usuario findUsuarioByEmail(String email);

    Usuario findUsuarioByCategoriaOrCategoria2(String categoria, String categoria2);

    UserDetails getByEmail(String email);

    @Query("{ 'cursoFavorito.0' : {$exists: true} }")
    List<Usuario> findUsuariosWithFavoritos();
}
