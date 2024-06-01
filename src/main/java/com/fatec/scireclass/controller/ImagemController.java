package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.service.Base64Service;
import com.fatec.scireclass.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping("/uploadImagem/curso/{cursoId}")
    public ResponseEntity<ImagemDTO> upload(@RequestBody String base64, @PathVariable String cursoId) throws IOException {
        return new ResponseEntity<>(imagemService.addImage(UUID.randomUUID().toString(),cursoId, Base64Service.base64ToInputStream(base64)), HttpStatus.OK);
    }

    @GetMapping("/{imagemId}")
    public ResponseEntity<ImagemDTO> getImagem(@PathVariable String imagemId) {
        return new ResponseEntity<>(imagemService.getImagem(imagemId), HttpStatus.OK);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ImagemDTO> getImagemByCursoId(@PathVariable String cursoId) {
        return new ResponseEntity<>(imagemService.getImagemByCursoId(cursoId), HttpStatus.OK);
    }

    @GetMapping("/downloadImage")
    public ResponseEntity<Resource> downlaod(@RequestParam String path) {
        ByteArrayResource resource = new ByteArrayResource(imagemService.getFile(path));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
    }

    @DeleteMapping("/{imagemId}/{path}")
    public ResponseEntity<?> deleteAula(@PathVariable String imagemId, @PathVariable String path){
        imagemService.deleteImage(imagemId,path);
        return ResponseEntity.noContent().build();
    }
}
