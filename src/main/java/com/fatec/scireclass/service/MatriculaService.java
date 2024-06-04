package com.fatec.scireclass.service;

import java.util.List;

import com.fatec.scireclass.model.dto.CursoDTO;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.model.dto.MatriculaDTO;

@Service
public interface MatriculaService {
    
    MatriculaDTO salvarMatricula(String usuarioId, String cursoId);

    Matricula buscarMatricula(String id);

    Integer gerarNumeroMatricula();

    MatriculaDTO encerraMatricula(String id);

    MatriculaDTO encontramatriculaporAlunoECurso(String usuarioId,String cursoid);

    List<MatriculaDTO> encontrarMatriculaFinalizada(String usuarioId);

    List<MatriculaDTO> encontrarMatriculas(String usuarioId);

    List<MatriculaDTO> encontrarMatriculasPorCriador(String usuarioId);

    List<MatriculaDTO> encontrarMatriculasPorCurso(String usuarioId, String cursoId);

    void excluirMatricula(String matriculaId);

    List<CursoDTO> encontrarMatriculasLastFive(String usuarioId);

    List<CursoDTO> findCursosByMatricula(String usuarioId);

    MatriculaDTO ativaMatricula(String cursoId, String alunoId, String chatId);

}
