package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.dto.CursoDTO;

public class CursoMapper {

    private CursoMapper(){
        throw new IllegalStateException("Utility class");
    }
    
    public static Curso cursoDTOToCurso(CursoDTO cursoDTO){
        Curso curso = new Curso();

        curso.setNome(cursoDTO.getNome());
        curso.setDescricao(cursoDTO.getDescricao());
        curso.setLink(cursoDTO.getLink());
        curso.setDuracao(cursoDTO.getDuracao());
        curso.setTelefone(cursoDTO.getTelefone());
        curso.setEmail(cursoDTO.getEmail());
        curso.setValor(cursoDTO.getValor());
        curso.setVagas(cursoDTO.getVagas());

        return curso;
    }

    public static CursoDTO cursoToCursoDTO(Curso curso){
        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setId(curso.getId());
        cursoDTO.setNome(curso.getNome());
        cursoDTO.setDescricao(curso.getDescricao());
        cursoDTO.setLink(curso.getLink());
        cursoDTO.setDuracao(curso.getDuracao());
        cursoDTO.setTelefone(curso.getTelefone());
        cursoDTO.setEmail(curso.getEmail());
        cursoDTO.setValor(curso.getValor());
        cursoDTO.setVagas(curso.getVagas());

        return cursoDTO;
    }
}
