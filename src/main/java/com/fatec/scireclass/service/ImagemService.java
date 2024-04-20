package com.fatec.scireclass.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Imagem;

@Service
public interface ImagemService {

    String addImage(String nome,String cursoID, MultipartFile file) throws IOException;
    Imagem getImagem(String id);

}
