package com.fatec.scireclass.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.dto.CursoFilterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.CadastroCursoDTO;
import com.fatec.scireclass.model.dto.CursoDTO;

@Service
public interface CursoService {
    CursoDTO cadastrarCurso(CadastroCursoDTO cadastroCursoDTO, String criadorId , InputStream inputStream) throws GeneralSecurityException, IOException;

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

    List<CursoDTO> getAllCursos();

    List<CursoDTO> cursosFilter(CursoFilterDTO cursoFilterDTO);

    CursoDTO avaliarCurso(CursoDTO cursoDTO);
}
