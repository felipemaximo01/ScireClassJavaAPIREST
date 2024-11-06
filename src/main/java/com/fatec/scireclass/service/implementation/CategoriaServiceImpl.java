package com.fatec.scireclass.service.implementation;

import java.util.List;

import com.fatec.scireclass.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.dto.CategoriaDTO;
import com.fatec.scireclass.model.mapper.CategoriaMapper;
import com.fatec.scireclass.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Categoria categoriaPorId(String categoriaId) {
        return this.categoriaRepository.findCategoriaById(categoriaId);
    }

    @Override
    public List<Categoria> categorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria saveCategoria(CategoriaDTO categoriaDTO) {
        return categoriaRepository.save(CategoriaMapper.categoriaDTOToCategoria(categoriaDTO));
    }
    
    
}
