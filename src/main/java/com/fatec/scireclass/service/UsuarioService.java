package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.UsuarioDTO;

@Service
public interface UsuarioService {
    Usuario cadastrar(UsuarioDTO usuario);

    Boolean encontrarEmail(String email);

    Usuario usuarioPorEmail(String email);

    boolean validaEmail(UsuarioDTO usuarioDTO);

    Usuario login(String email, String senha);

    Usuario favoritaCurso(String idUsuario,String idCurso);

    Usuario desfavoritaCurso(String idUsuario, String idCurso);

    Usuario alteraDados(UsuarioDTO usuarioDTO, String usuarioId);

    Usuario encontraUsuario(String email, String senha);

    Usuario encontrarId(String usuarioId);

    Boolean favoritado(String usuarioId, String cursoId);

    Boolean excluirUsuario(String usuarioId);

    Usuario mudarSenha(TokenSenhaReset tokenSenhaReset, String senha);

    Usuario salvaUsuario(Usuario usuario);
}
