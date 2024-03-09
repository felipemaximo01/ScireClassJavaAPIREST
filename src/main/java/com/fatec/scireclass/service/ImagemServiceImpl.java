package com.fatec.scireclass.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Imagem;
import com.fatec.scireclass.repository.ImagemRepository;

@Service
public class ImagemServiceImpl implements ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    @Override
    public String addImage(String nome,Curso curso, MultipartFile file) throws IOException {
        Imagem img = new Imagem();
        img.setNome(nome);
        img.setCurso(curso);
        img.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        img = imagemRepository.save(img);
        return img.getId();
    }

    @Override
    public Imagem getImagem(String id) {
        return imagemRepository.findById(id).get();
    }


    
}