package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<EnderecoDTO> enderecoByUsuario(@PathVariable String usuarioId) {
        return new ResponseEntity<>(enderecoService.enderecoByUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<EnderecoDTO> enderecoByCurso(@PathVariable String cursoId) {
        return new ResponseEntity<>(enderecoService.enderecoByCurso(cursoId), HttpStatus.OK);
    }

}
