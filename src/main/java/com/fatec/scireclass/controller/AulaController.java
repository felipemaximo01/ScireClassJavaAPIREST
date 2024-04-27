package com.fatec.scireclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scireclass.model.dto.AulaDTO;
import com.fatec.scireclass.service.AulaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/aula")
public class AulaController {
    @Autowired
    private AulaService aulaService;

    @GetMapping("/getlistaulas/{cursoId}")
    public ResponseEntity<List<AulaDTO>> getAulas(@PathVariable String cursoId){
        return new ResponseEntity<>(aulaService.getAulas(cursoId), HttpStatus.OK);
    }

    @GetMapping("/getaula/{aulaId}")
    public ResponseEntity<AulaDTO> getAula(@PathVariable String aulaId){
        return new ResponseEntity<>(aulaService.getAula(aulaId), HttpStatus.OK);
    }

    @PostMapping("/save/{cursoId}")
    public ResponseEntity<AulaDTO> createAula(@RequestBody AulaDTO aulaDTO, @PathVariable String cursoId) {
        return new ResponseEntity<>(aulaService.createAula(aulaDTO, cursoId), HttpStatus.OK);
    }

    @PutMapping("/update/{aulaId}")
    public ResponseEntity<AulaDTO> updateAula(@RequestBody AulaDTO aulaDTO, @PathVariable String aulaId) {
        return new ResponseEntity<>(aulaService.updateAula(aulaDTO, aulaId), HttpStatus.OK);
    }

    @GetMapping("/delete/{aulaId}")
    public ResponseEntity<?> deleteAula(@PathVariable String aulaId){
        aulaService.deleteAula(aulaId);
        return ResponseEntity.noContent().build();
    }
    
}
