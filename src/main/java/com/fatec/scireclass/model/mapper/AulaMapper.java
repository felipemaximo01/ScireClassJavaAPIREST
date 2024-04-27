package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Aula;
import com.fatec.scireclass.model.dto.AulaDTO;

public class AulaMapper {
    private AulaMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Aula aulaDTOToAula(AulaDTO aulaDTO){
        Aula aula = new Aula();
        if(aulaDTO.getNome() != null)
            aula.setNome(aulaDTO.getNome());
        if(aulaDTO.getDescricao() != null)
            aula.setDescricao(aulaDTO.getDescricao());
        if(aulaDTO.getOrdem() != null)
            aula.setOrdem(aulaDTO.getOrdem());

        return aula;
    }

    public static AulaDTO aulaToAulaDTO(Aula aula){
        AulaDTO aulaDTO = new AulaDTO();
        if(aula.getId() != null)
            aulaDTO.setId(aula.getId());
        if(aula.getNome() != null)
            aulaDTO.setNome(aula.getNome());
        if(aula.getDescricao() != null)
            aulaDTO.setDescricao(aula.getDescricao());
        if(aula.getOrdem() != null)
            aulaDTO.setOrdem(aula.getOrdem());

        return aulaDTO;
    }
}
