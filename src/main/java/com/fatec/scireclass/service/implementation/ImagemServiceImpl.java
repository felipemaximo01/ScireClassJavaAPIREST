package com.fatec.scireclass.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.fatec.scireclass.model.dto.ImagemDTO;
import com.fatec.scireclass.model.mapper.ImagemMapper;
import com.fatec.scireclass.service.AzureBlobStorageService;
import com.fatec.scireclass.service.ImagemService;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ImagemDTO addImage(String nome,String cursoID, InputStream inputStream) throws IOException {
        Curso curso = cursoRepository.findCursoById(cursoID);
        if(curso == null)
            throw new CursoNotFoundException("O curso com ID: " +cursoID+" não foi encontrado");
        String path = azureBlobStorageService.write(inputStream,"curso/"+cursoID+"/"+nome+ UUID.randomUUID().toString()+".png");
        Imagem img = new Imagem();
        if(curso.getImagem() != null && curso.getImagem().getId() != null){
            img = imagemRepository.findByCursoId(curso.getId());
            img.setNome(nome);
            img.setCurso(curso);
            img.setPath(path);
            img = imagemRepository.save(img);
            curso.setImagem(img);
            cursoRepository.save(curso);
        }else{
            img.setNome(nome);
            img.setCurso(curso);
            img.setPath(path);
            img = imagemRepository.save(img);
            curso.setImagem(img);
            cursoRepository.save(curso);
        }
        return ImagemMapper.imagemToImagemDTO(img);
    }

    @Override
    public ImagemDTO getImagem(String id) {
        Imagem imagem = imagemRepository.findImagemById(id);
        if(imagem == null)
            throw new ResourceNotFoundException("Imagem não encontrada");
        return ImagemMapper.imagemToImagemDTO(imagem);
    }

    @Override
    public ImagemDTO getImagemByCursoId(String id) {
        Curso curso = cursoRepository.findCursoById(id);
        if(curso == null)
            throw new ResourceNotFoundException("Curso não encontrado");
        Imagem imagem = imagemRepository.findByCursoId(curso.getId());
        if(imagem == null)
            throw new ResourceNotFoundException("Imagem não encontrada");
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