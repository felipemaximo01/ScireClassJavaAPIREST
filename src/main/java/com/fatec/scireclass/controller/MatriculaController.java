package com.fatec.scireclass.controller;

import java.util.List;

import com.fatec.scireclass.model.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.model.dto.MatriculaDTO;
import com.fatec.scireclass.model.mapper.MatriculaMapper;
import com.fatec.scireclass.service.MatriculaService;
import com.fatec.scireclass.service.exceptions.MatriculaNotFoundException;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

 
    @PostMapping("/save/{usuarioId}/{cursoId}")
    public ResponseEntity<MatriculaDTO> fazerMatricula(@PathVariable String usuarioId, @PathVariable String cursoId){
        return new ResponseEntity<>(matriculaService.salvarMatricula(usuarioId,cursoId), HttpStatus.OK);
    }

  
    @GetMapping("/find/{matriculaId}")
    public ResponseEntity<MatriculaDTO> buscarMatricula(@PathVariable String matriculaId){
        Matricula matricula = matriculaService.buscarMatricula(matriculaId);
        if(matricula == null)
            throw new MatriculaNotFoundException("Não foi possivel encontrar a matricula com o ID: " + matriculaId);
        return new ResponseEntity<>(MatriculaMapper.matriculaToMatriculaDTO(matricula), HttpStatus.OK);
    }

   
    @GetMapping("/findalunoandcurso/{usuarioId}/{cursoId}")
    public ResponseEntity<MatriculaDTO> buscarMatriculaPorAlunoCurso(@PathVariable String usuarioId, @PathVariable String cursoId){
        return new ResponseEntity<>(this.matriculaService.encontramatriculaporAlunoECurso(usuarioId,cursoId), HttpStatus.OK);
    }

    @GetMapping("/findfim/{usuarioId}")
    public ResponseEntity<List<MatriculaDTO>> buscarMatriculaEncerrada(@PathVariable String usuarioId){
        return new ResponseEntity<>(this.matriculaService.encontrarMatriculaFinalizada(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/matriculas/{usuarioId}")
    public ResponseEntity<List<MatriculaDTO>> matriculas(@PathVariable String usuarioId){
        return new ResponseEntity<>(this.matriculaService.encontrarMatriculas(usuarioId), HttpStatus.OK);

    }

    @GetMapping("/matriculasporcriador/{usuarioId}")
    public ResponseEntity<List<MatriculaDTO>> matriculasPorCriador(@PathVariable String usuarioId){
        return new ResponseEntity<>(this.matriculaService.encontrarMatriculasPorCriador(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/matriculasporcurso/{usuarioId}/{cursoId}")
    public ResponseEntity<List<MatriculaDTO>> matriculasPorCurso(@PathVariable String usuarioId, @PathVariable String cursoId){
        return new ResponseEntity<>(this.matriculaService.encontrarMatriculasPorCurso(usuarioId,cursoId), HttpStatus.OK);
    }

    @DeleteMapping("/cancelamatricula/{matriculaId}")
    public void cancelaMatricula(@PathVariable String matriculaId){
        this.matriculaService.excluirMatricula(matriculaId);
    }

    @PutMapping("/encerramatricula/{matriculaId}")
    public ResponseEntity<MatriculaDTO> encerraMatricula(@PathVariable String matriculaId){
        return new ResponseEntity<>(this.matriculaService.encerraMatricula(matriculaId), HttpStatus.OK);
    }

    @GetMapping("/curso/{usuarioId}")
    public ResponseEntity<List<CursoDTO>> encontrarMatriculasLastFive(@PathVariable String usuarioId){
        return new ResponseEntity<>(this.matriculaService.encontrarMatriculasLastFive(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/curso/all/{usuarioId}")
    public ResponseEntity<List<CursoDTO>> findCursosByMatricula(@PathVariable String usuarioId){
        return new ResponseEntity<>(matriculaService.findCursosByMatricula(usuarioId), HttpStatus.OK);
    }
}
