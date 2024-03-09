package com.fatec.scireclass.service;

import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.repository.TokenSenhaResetRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenSenhaResetServiceImpl implements TokenSenhaResetService {


    private TokenSenhaResetRepository tokenSenhaResetRepository;

    public void criarTokenSenha(Usuario usuario, String token) {
        TokenSenhaReset tokenNovo = new TokenSenhaReset();
        tokenNovo.setUsuario(usuario);
        tokenNovo.setToken(token);
        tokenSenhaResetRepository.save(tokenNovo);
    }

    @Override
    public TokenSenhaReset getTokenSenha(String token) {
        return tokenSenhaResetRepository.findByToken(token);
    }

    @Override
    public void deletaTokenSenha(String id) {
        tokenSenhaResetRepository.deleteTokenSenhaByUsuarioEmail(id);
    }

}
