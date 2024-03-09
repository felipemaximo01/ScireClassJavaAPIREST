package com.fatec.scireclass.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Usuario;

@Service
public interface CursoService {
    Curso cadastrarCurso(Curso curso, MultipartFile file) throws GeneralSecurityException, IOException;

    List<Curso> encontrarDesc(String desc);

    List<Curso> encontrarNome(String nome);

    Curso encontrarId(String id);

    void deletaCurso(String id);

    List<Curso> topCurso();

    List<Curso> cursodaCategoria(Categoria categoria);

    Curso alterarDadosCurso(Curso curso);

    List<Curso> cursosFavoritos(Usuario usuario);

    List<Curso> cursosCriador(String usuarioId);

    Boolean excluirCurso(String cursoId, String usuarioId);
}
