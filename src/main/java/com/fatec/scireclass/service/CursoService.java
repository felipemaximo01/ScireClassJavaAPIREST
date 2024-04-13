package com.fatec.scireclass.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.CursoDTO;

@Service
public interface CursoService {
    CursoDTO cadastrarCurso(CursoDTO cursoDTO, MultipartFile file) throws GeneralSecurityException, IOException;

    List<CursoDTO> encontrarDesc(String desc);

    List<CursoDTO> encontrarNome(String nome);

    CursoDTO encontrarId(String id);

    void deletaCurso(String id);

    List<CursoDTO> topCurso();

    List<CursoDTO> cursodaCategoria(String categoriaId);

    CursoDTO alterarDadosCurso(CursoDTO cursoDTO,String cursoId);

    List<CursoDTO> cursosFavoritos(Usuario usuario);

    List<CursoDTO> cursosCriador(Usuario usuario);

    Boolean excluirCurso(String cursoId, Usuario usuario);
}
