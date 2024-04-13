package com.fatec.scireclass.service;

import com.fatec.scireclass.model.TokenVerificacao;
import com.fatec.scireclass.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface TokenVerificacaoService {

    void criarTokenVerificacao(Usuario usuario,String token);

    TokenVerificacao getTokenVerificacao(String tokenVerificacao);

    void deletaTokenVerificacao(String id);

}
