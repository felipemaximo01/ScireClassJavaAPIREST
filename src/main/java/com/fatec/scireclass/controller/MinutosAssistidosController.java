package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.MinutosAssistidosDTO;
import com.fatec.scireclass.service.MinutosAssistidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minutosAssistidos")
public class MinutosAssistidosController {

    @Autowired
    private MinutosAssistidosService minutosAssistidosService;

    @GetMapping("/{alunoId}")
    public ResponseEntity<MinutosAssistidosDTO> getMinutosAssistidos(@PathVariable String alunoId) {
        return new ResponseEntity<>(minutosAssistidosService.findByAlunoId(alunoId), HttpStatus.OK);
    }

    @PostMapping("/{alunoId}")
    public ResponseEntity<MinutosAssistidosDTO> save(@RequestBody MinutosAssistidosDTO minutosAssistidosDTO,@PathVariable String alunoId) {
        return new ResponseEntity<>(minutosAssistidosService.save(minutosAssistidosDTO ,alunoId), HttpStatus.OK);
    }
}
