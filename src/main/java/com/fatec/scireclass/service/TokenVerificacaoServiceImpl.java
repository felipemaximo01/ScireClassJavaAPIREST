package com.fatec.scireclass.service;

import com.fatec.scireclass.model.TokenVerificacao;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.repository.TokenVerificacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenVerificacaoServiceImpl implements TokenVerificacaoService {

    private TokenVerificacaoRepository tokenVerificacaoRepository;

    @Override
    public void criarTokenVerificacao(Usuario usuario, String token) {
        TokenVerificacao tokenNovo = new TokenVerificacao();
        tokenNovo.setUsuario(usuario);
        tokenNovo.setToken(token);
        tokenVerificacaoRepository.save(tokenNovo);
    }

    @Override
    public TokenVerificacao getTokenVerificacao(String tokenVerificacao) {
        return tokenVerificacaoRepository.findByToken(tokenVerificacao);
    }

    @Override
    public void deletaTokenVerificacao(String id) {
        tokenVerificacaoRepository.deleteTokenVerificacaoByUsuarioId(id);
    }
}
