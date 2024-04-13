package com.fatec.scireclass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.scireclass.model.TokenVerificacao;
import com.fatec.scireclass.model.Usuario;

@Repository
public interface TokenVerificacaoRepository extends MongoRepository<TokenVerificacao,String> {

    TokenVerificacao findByToken(String token);

    TokenVerificacao findByUsuario(Usuario usuario);

    void deleteTokenVerificacaoByUsuarioId(String id);
    
}
