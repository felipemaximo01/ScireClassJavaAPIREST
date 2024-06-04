package com.fatec.scireclass.service;

import com.fatec.scireclass.model.Usuario;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.dto.EnderecoDTO;

@Service
public interface EnderecoService {
    Endereco salvaEndereco(EnderecoDTO enderecoDTO);

    EnderecoDTO enderecoByUsuario(String usuarioId);

    EnderecoDTO enderecoByCurso(String cursoId);
}
