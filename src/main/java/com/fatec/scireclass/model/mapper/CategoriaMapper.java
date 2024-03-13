package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.dto.CategoriaDTO;

public class CategoriaMapper {
    private CategoriaMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();

        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());

        return categoria;
    }

    public static CategoriaDTO categoriaToCategoriaDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNome(categoria.getNome());
        categoriaDTO.setDescricao(categoria.getDescricao());

        return categoriaDTO;
    }
}
