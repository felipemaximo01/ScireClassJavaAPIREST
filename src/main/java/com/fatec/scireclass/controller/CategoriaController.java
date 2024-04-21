package com.fatec.scireclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scireclass.model.dto.CategoriaDTO;
import com.fatec.scireclass.model.mapper.CategoriaMapper;
import com.fatec.scireclass.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDTO> findBtId(@PathVariable String categoriaId) {
        return new ResponseEntity<>(
            CategoriaMapper.categoriaToCategoriaDTO(categoriaService.categoriaPorId(categoriaId)), 
            HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        return new ResponseEntity<>(
            categoriaService.categorias()
                .stream()
                .map(x -> CategoriaMapper.categoriaToCategoriaDTO(x))
                .toList(),
            HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> saveCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return new ResponseEntity<>(CategoriaMapper
            .categoriaToCategoriaDTO(categoriaService.saveCategoria(categoriaDTO)), 
            HttpStatus.OK);
    }
}
