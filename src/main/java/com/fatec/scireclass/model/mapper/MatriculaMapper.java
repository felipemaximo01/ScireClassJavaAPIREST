package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.model.dto.MatriculaDTO;

public class MatriculaMapper {

    private MatriculaMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Matricula matriculaDTOToMatricula(MatriculaDTO matriculaDTO){
        Matricula matricula = new Matricula();

        matricula.setNumeroMatricula(matriculaDTO.getNumeroMatricula());
        matricula.setAtivo(matriculaDTO.getAtivo());
        matricula.setDataFim(matriculaDTO.getDataFim());
        matricula.setDataInicio(matriculaDTO.getDataInicio());

        return matricula;
    }

    
    public static MatriculaDTO matriculaToMatriculaDTO(Matricula matricula){
        MatriculaDTO matriculaDTO = new MatriculaDTO();

        matriculaDTO.setId(matricula.getId());
        matriculaDTO.setNumeroMatricula(matricula.getNumeroMatricula());
        matriculaDTO.setAtivo(matricula.getAtivo());
        if(matricula.getDataFim() != null)
            matriculaDTO.setDataFim(matricula.getDataFim());
        matriculaDTO.setDataInicio(matricula.getDataInicio());

        return matriculaDTO;
    }

}
