package com.fatec.scireclass.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.repository.EnderecoRepository;

public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco salvaEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = EnderecoMapper.enderecoDTOToEndereco(enderecoDTO);
        return enderecoRepository.save(endereco);
    }
    
}
