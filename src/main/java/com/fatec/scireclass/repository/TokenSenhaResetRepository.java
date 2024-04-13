package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.Usuario;

@Repository
public interface TokenSenhaResetRepository extends MongoRepository<TokenSenhaReset,String>{
        
    TokenSenhaReset findByToken(String token);

    TokenSenhaReset findByUsuario(Usuario usuario);

    void deleteTokenSenhaByUsuarioEmail(String email);
        
}
