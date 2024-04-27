package com.fatec.scireclass.service;

import java.io.IOException;

import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.model.mapper.ImagemMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
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
    @Autowired
    private AzureBlobStorageService azureBlobStorageService;

    @Override
    public ImagemDTO addImage(String nome,String cursoID, MultipartFile file) throws IOException {
        Curso curso = cursoRepository.findById(cursoID).get();
        if(curso == null)
            throw new CursoNotFoundException("O curso com ID: " +cursoID+" não foi encontrado");
        String path = azureBlobStorageService.write(file,"curso/"+cursoID+"/"+nome+file.getOriginalFilename());

        Imagem img = new Imagem();
        img.setNome(nome);
        img.setCurso(curso);
        img.setPath(path);
        img = imagemRepository.save(img);
        curso.setImagem(img);
        cursoRepository.save(curso);
        return ImagemMapper.imagemToImagemDTO(img);
    }

    @Override
    public ImagemDTO getImagem(String id) {
        Imagem imagem = imagemRepository.findById(id).get();
        if(imagem == null)
            throw new ResourceAccessException("Imagem não encontrada");
        return ImagemMapper.imagemToImagemDTO(imagem);
    }

    @Override
    public byte[] getFile(String path) {
        return azureBlobStorageService.read(path);
    }

    @Override
    public void deleteImage(String path, String imagemId) {
        imagemRepository.deleteById(imagemId);
        azureBlobStorageService.delete(path);
    }


}