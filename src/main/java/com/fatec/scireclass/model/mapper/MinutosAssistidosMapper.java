package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.MinutosAssistidos;
import com.fatec.scireclass.model.dto.CategoriaDTO;
import com.fatec.scireclass.model.dto.MinutosAssistidosDTO;

public class MinutosAssistidosMapper {
    private MinutosAssistidosMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static MinutosAssistidos minutosAssistidosDTOTominutosAssistidos(MinutosAssistidosDTO minutosAssistidosDTO){
        MinutosAssistidos minutosAssistidos = new MinutosAssistidos();
        if(minutosAssistidosDTO.getMinutos() != null)
            minutosAssistidos.setMinutos(minutosAssistidosDTO.getMinutos());
        if(minutosAssistidosDTO.getData() != null)
            minutosAssistidos.setData(minutosAssistidosDTO.getData());

        return minutosAssistidos;
    }

    public static MinutosAssistidosDTO minutosAssistidosTominutosAssistidosDTO(MinutosAssistidos minutosAssistidos){
        MinutosAssistidosDTO minutosAssistidosDTO = new MinutosAssistidosDTO();
        if(minutosAssistidos.getId() !=null)
            minutosAssistidosDTO.setId(minutosAssistidos.getId());
        if(minutosAssistidos.getMinutos() != null)
            minutosAssistidosDTO.setMinutos(minutosAssistidos.getMinutos());
        if(minutosAssistidos.getData() != null)
            minutosAssistidosDTO.setData(minutosAssistidos.getData());

        return minutosAssistidosDTO;
    }
}
