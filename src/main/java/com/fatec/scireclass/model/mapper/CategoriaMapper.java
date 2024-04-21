package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.dto.CategoriaDTO;

public class CategoriaMapper {
    private CategoriaMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        if(categoriaDTO.getNome() != null)
            categoria.setNome(categoriaDTO.getNome());
        if(categoriaDTO.getDescricao() != null)
            categoria.setDescricao(categoriaDTO.getDescricao());

        return categoria;
    }

    public static CategoriaDTO categoriaToCategoriaDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        if(categoria.getId() != null)
            categoriaDTO.setId(categoria.getId());
        if(categoria.getNome() != null)
            categoriaDTO.setNome(categoria.getNome());
        if(categoria.getDescricao() != null)
            categoriaDTO.setDescricao(categoria.getDescricao());

        return categoriaDTO;
    }
}
