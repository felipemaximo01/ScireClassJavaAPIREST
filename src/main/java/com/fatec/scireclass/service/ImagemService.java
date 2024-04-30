package com.fatec.scireclass.service;

import java.io.IOException;
import java.io.InputStream;

import com.fatec.scireclass.model.dto.ImagemDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Imagem;

@Service
public interface ImagemService {

    ImagemDTO addImage(String nome, String cursoID, InputStream inputStream) throws IOException;
    ImagemDTO getImagem(String id);
    ImagemDTO getImagemByCursoId(String id);
    byte[] getFile(String path);
    void deleteImage(String path, String imagemId);
}
