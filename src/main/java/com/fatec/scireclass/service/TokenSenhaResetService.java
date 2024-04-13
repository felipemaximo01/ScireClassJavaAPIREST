package com.fatec.scireclass.service;

import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface TokenSenhaResetService {

    void criarTokenSenha(Usuario usuario,String token);

    TokenSenhaReset getTokenSenha(String token);

    void deletaTokenSenha(String email);




}
