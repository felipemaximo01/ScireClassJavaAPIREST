package com.fatec.scireclass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.dto.CategoriaDTO;

@Service
public interface CategoriaService {
    Categoria categoriaPorId(String categoriaId);

    List<Categoria> categorias();

    Categoria saveCategoria(CategoriaDTO categoriaDTO);
}
