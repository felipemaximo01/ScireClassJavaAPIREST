package com.fatec.scireclass.controller;

import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.model.dto.VideoDTO;
import com.fatec.scireclass.service.ImagemService;
import com.fatec.scireclass.service.VideoService;
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
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/uploadvideo/{aulaId}")
    public ResponseEntity<VideoDTO> upload(@RequestParam MultipartFile file, @PathVariable String aulaId) throws IOException, InterruptedException {
        return new ResponseEntity<>(videoService.addVideo(aulaId,file), HttpStatus.OK);
    }

    @GetMapping("/{aulaId}")
    public ResponseEntity<VideoDTO> getImagem(@PathVariable String aulaId) {
        return new ResponseEntity<>(videoService.getVideo(aulaId), HttpStatus.OK);
    }

    @GetMapping("/downloadvideo")
    public ResponseEntity<Resource> downlaod(@RequestParam String path) {
        ByteArrayResource resource = new ByteArrayResource(videoService.getFile(path));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
    }

    @DeleteMapping("/{videoId}/{path}")
    public ResponseEntity<?> deleteAula(@PathVariable String videoId, @PathVariable String path){
        videoService.deleteVideo(videoId,path);
        return ResponseEntity.noContent().build();
    }
    
}
