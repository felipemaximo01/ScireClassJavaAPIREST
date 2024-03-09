package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Usuario;

@Service
public interface UsuarioService {
    Usuario cadastrar(Usuario usuario);

    Boolean encontrarEmail(String email);

    Usuario usuarioPorEmail(String email);

    boolean validaEmail(Usuario usuario);

    Usuario login(String email, String senha);

    Usuario favoritaCurso(String idUsuario,String idCurso);

    Usuario desfavoritaCurso(String idUsuario, String idCurso);

    Usuario alteraDados(Usuario usuario);

    Usuario encontraUsuario(String email, String senha);

    Usuario encontrarId(String usuarioId);

    Boolean favoritado(String usuarioId, String cursoId);

    Boolean excluirUsuario(String usuarioId);
}
