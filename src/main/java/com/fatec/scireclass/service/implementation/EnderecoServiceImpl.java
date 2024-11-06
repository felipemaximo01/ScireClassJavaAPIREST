package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.EnderecoService;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Endereco salvaEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = EnderecoMapper.enderecoDTOToEndereco(enderecoDTO);
        return enderecoRepository.save(endereco);
    }

    @Override
    public EnderecoDTO enderecoByUsuario(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if (usuario == null)
            throw new ResourceNotFoundException("Usuário não encontrado!");
        Endereco endereco = enderecoRepository.findByUsuario(usuario);
        if (endereco == null)
            throw new ResourceNotFoundException("Endereço não encontrado");

        return EnderecoMapper.enderecoToEnderecoDTO(endereco);
    }

    @Override
    public EnderecoDTO enderecoByCurso(String cursoId) {
        Curso curso = cursoRepository.findCursoById(cursoId);
        if (cursoId == null)
            throw new ResourceNotFoundException("Curso não encontrado!");
        Endereco endereco = enderecoRepository.findByCurso(curso);
        if (endereco == null)
            throw new ResourceNotFoundException("Endereço não encontrado");

        return EnderecoMapper.enderecoToEnderecoDTO(endereco);
    }

}
