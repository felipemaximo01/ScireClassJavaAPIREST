package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.dto.EnderecoDTO;

public class EnderecoMapper {

    private EnderecoMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Endereco enderecoDTOToEndereco(EnderecoDTO enderecoDTO){
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoDTO.getCep());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLocalidade(enderecoDTO.getLocalidade());
        endereco.setUf(enderecoDTO.getUf());

        return endereco;
    }

    public static EnderecoDTO enderecoToEnderecoDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setLocalidade(endereco.getLocalidade());
        enderecoDTO.setUf(endereco.getUf());

        return enderecoDTO;
    }
}
