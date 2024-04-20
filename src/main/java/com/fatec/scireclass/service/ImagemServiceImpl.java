package com.fatec.scireclass.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Imagem;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.ImagemRepository;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;

@Service
public class ImagemServiceImpl implements ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public String addImage(String nome,String cursoID, MultipartFile file) throws IOException {
        Curso curso = cursoRepository.findById(cursoID).get();
        if(curso == null)
            throw new CursoNotFoundException("O curso com ID: " +cursoID+" não foi encontrado");
        Imagem img = new Imagem();
        img.setNome(nome);
        img.setCurso(curso);
        img.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        img = imagemRepository.save(img);
        curso.setImagem(img);
        cursoRepository.save(curso);
        return img.getId();
    }

    @Override
    public Imagem getImagem(String id) {
        return imagemRepository.findById(id).get();
    }


    
}