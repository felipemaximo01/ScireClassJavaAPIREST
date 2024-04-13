package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Categoria;

@Service
public interface CategoriaService {
    Categoria categoriaPorId(String categoriaId);
}
