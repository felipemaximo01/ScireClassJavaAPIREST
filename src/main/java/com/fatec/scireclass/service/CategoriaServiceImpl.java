package com.fatec.scireclass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements  CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Categoria categoriaPorId(String categoriaId) {
        return this.categoriaRepository.findById(categoriaId).get();
    }
    
}
