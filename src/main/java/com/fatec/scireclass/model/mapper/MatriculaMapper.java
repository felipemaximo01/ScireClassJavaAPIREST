package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.model.dto.MatriculaDTO;

public class MatriculaMapper {

    private MatriculaMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Matricula matriculaDTOToMatricula(MatriculaDTO matriculaDTO){
        Matricula matricula = new Matricula();
        if(matriculaDTO.getNumeroMatricula() != null)
            matricula.setNumeroMatricula(matriculaDTO.getNumeroMatricula());
        if(matriculaDTO.getAtivo() != null)
            matricula.setAtivo(matriculaDTO.getAtivo());
        if(matriculaDTO.getDataFim() != null)
            matricula.setDataFim(matriculaDTO.getDataFim());
        if(matriculaDTO.getDataInicio() != null)
            matricula.setDataInicio(matriculaDTO.getDataInicio());

        return matricula;
    }

    
    public static MatriculaDTO matriculaToMatriculaDTO(Matricula matricula){
        MatriculaDTO matriculaDTO = new MatriculaDTO();
        if(matricula.getId() != null)
            matriculaDTO.setId(matricula.getId());
        if(matricula.getNumeroMatricula() != null)
            matriculaDTO.setNumeroMatricula(matricula.getNumeroMatricula());
        if(matricula.getAtivo() != null)
            matriculaDTO.setAtivo(matricula.getAtivo());
        if(matricula.getDataFim() != null)
            matriculaDTO.setDataFim(matricula.getDataFim());
        if(matricula.getDataInicio() != null)
            matriculaDTO.setDataInicio(matricula.getDataInicio());

        return matriculaDTO;
    }

}
