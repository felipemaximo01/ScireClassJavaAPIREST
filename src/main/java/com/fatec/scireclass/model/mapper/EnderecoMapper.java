package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.dto.EnderecoDTO;

public class EnderecoMapper {

    private EnderecoMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Endereco enderecoDTOToEndereco(EnderecoDTO enderecoDTO){
        Endereco endereco = new Endereco();
        if(enderecoDTO.getCep() != null)
            endereco.setCep(enderecoDTO.getCep());
        if(enderecoDTO.getLogradouro() != null)
            endereco.setLogradouro(enderecoDTO.getLogradouro());
        if(enderecoDTO.getComplemento() != null)
            endereco.setComplemento(enderecoDTO.getComplemento());
        if(enderecoDTO.getBairro() != null)
            endereco.setBairro(enderecoDTO.getBairro());
        if(enderecoDTO.getLocalidade() != null)
            endereco.setLocalidade(enderecoDTO.getLocalidade());
        if(enderecoDTO.getUf() != null)
            endereco.setUf(enderecoDTO.getUf());
        if(enderecoDTO.getNumero() != null)
            endereco.setNumero(enderecoDTO.getNumero());

        return endereco;
    }

    public static EnderecoDTO enderecoToEnderecoDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        if(endereco.getId() != null) 
            enderecoDTO.setId(endereco.getId());
        if(endereco.getCep() != null)
            enderecoDTO.setCep(endereco.getCep());
        if(endereco.getLogradouro() != null)
            enderecoDTO.setLogradouro(endereco.getLogradouro());
        if(endereco.getComplemento() != null)
            enderecoDTO.setComplemento(endereco.getComplemento());
        if(endereco.getBairro() != null)
            enderecoDTO.setBairro(endereco.getBairro());
        if(endereco.getLocalidade() != null)
            enderecoDTO.setLocalidade(endereco.getLocalidade());
        if(endereco.getUf() != null)
            enderecoDTO.setUf(endereco.getUf());
        if(endereco.getNumero() != null)
            enderecoDTO.setNumero(endereco.getNumero());

        return enderecoDTO;
    }
}
