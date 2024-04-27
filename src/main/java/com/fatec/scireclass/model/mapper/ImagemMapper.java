package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Imagem;
import com.fatec.scireclass.model.dto.ImagemDTO;

public class ImagemMapper {
    private ImagemMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Imagem imagemDTOToImagem(ImagemDTO imagemDTO){
        Imagem imagem = new Imagem();
        if(imagemDTO.getNome() != null)
            imagem.setNome(imagemDTO.getNome());
        if(imagemDTO.getPath() != null)
            imagem.setPath(imagemDTO.getPath());
        return imagem;
    }

    public static ImagemDTO imagemToImagemDTO(Imagem imagem){
        ImagemDTO imagemDTO = new ImagemDTO();
        if(imagem.getNome() != null)
            imagemDTO.setNome(imagem.getNome());
        if(imagem.getPath() != null)
            imagemDTO.setPath(imagem.getPath());
        if(imagem.getId() != null)
            imagemDTO.setId(imagem.getId());
        return imagemDTO;
    }
}
