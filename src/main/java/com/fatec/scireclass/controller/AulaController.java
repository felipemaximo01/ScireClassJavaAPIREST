package com.fatec.scireclass.controller;

import java.util.List;

import com.fatec.scireclass.model.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatec.scireclass.model.dto.AulaDTO;
import com.fatec.scireclass.service.AulaService;


@RestController
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

    @PostMapping("/matricula/{usuarioId}/{aulaId}")
    public ResponseEntity<VideoDTO> saveAulasAssistidas(@PathVariable String usuarioId, @PathVariable String aulaId) {
        return new ResponseEntity<>(aulaService.saveAulasAssistidas(usuarioId, aulaId), HttpStatus.OK);
    }

    @PutMapping("/update/{aulaId}")
    public ResponseEntity<AulaDTO> updateAula(@RequestBody AulaDTO aulaDTO, @PathVariable String aulaId) {
        return new ResponseEntity<>(aulaService.updateAula(aulaDTO, aulaId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{aulaId}")
    public ResponseEntity<?> deleteAula(@PathVariable String aulaId){
        aulaService.deleteAula(aulaId);
        return ResponseEntity.noContent().build();
    }
    
}
