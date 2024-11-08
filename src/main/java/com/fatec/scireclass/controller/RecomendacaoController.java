package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recomendacao")
public class RecomendacaoController {
    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping("/{usuarioID}")
    public ResponseEntity<List<CursoDTO>> getRecomendacao(@PathVariable String usuarioID) {
        return new ResponseEntity<>(recomendacaoService.cursosRecomendados(usuarioID), HttpStatus.OK);
    }
}
