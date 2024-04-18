package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Mensagem;
import com.fatec.scireclass.model.dto.MensagemDTO;

public class MensagemMapper {

    private MensagemMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Mensagem mensagemDTOToMensagem(MensagemDTO mensagemDTO){
        Mensagem mensagem = new Mensagem();
        if(mensagemDTO.getId() != null)
            mensagem.setId(mensagemDTO.getId());
        if(mensagemDTO.getInstante() != null)
            mensagem.setInstante(mensagemDTO.getInstante());
        if(mensagemDTO.getMensagens() != null)
            mensagem.setMensagens(mensagemDTO.getMensagens());

        return mensagem;
    }

    public static MensagemDTO mensagemToMensagemDTO(Mensagem mensagem){
        MensagemDTO mensagemDTO = new MensagemDTO();
        if(mensagem.getId() != null)
            mensagemDTO.setId(mensagem.getId());
        if(mensagem.getInstante() != null)
            mensagemDTO.setInstante(mensagem.getInstante());
        if(mensagem.getMensagens() != null)
            mensagemDTO.setMensagens(mensagem.getMensagens());

        return mensagemDTO;
    }
}
