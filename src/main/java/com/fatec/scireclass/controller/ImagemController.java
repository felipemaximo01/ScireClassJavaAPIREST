package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagem")
@CrossOrigin(origins = "*")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping("/uploadImagem/curso/{cursoId}")
    public ResponseEntity<ImagemDTO> upload(@RequestParam MultipartFile file, @PathVariable String cursoId) throws IOException {
        return new ResponseEntity<>(imagemService.addImage(UUID.randomUUID().toString(),cursoId,file), HttpStatus.OK);
    }

    @GetMapping("/{imagemId}")
    public ResponseEntity<ImagemDTO> getImagem(@PathVariable String imagemId) {
        return new ResponseEntity<>(imagemService.getImagem(imagemId), HttpStatus.OK);
    }

    @GetMapping("/downloadImage/{path}")
    public ResponseEntity<byte[]> downlaod(@PathVariable String path) {
        return new ResponseEntity<>(imagemService.getFile(path), HttpStatus.OK);
    }

    @DeleteMapping("/{imagemId}/{path}")
    public ResponseEntity<?> deleteAula(@PathVariable String imagemId, @PathVariable String path){
        imagemService.deleteImage(imagemId,path);
        return ResponseEntity.noContent().build();
    }
}
