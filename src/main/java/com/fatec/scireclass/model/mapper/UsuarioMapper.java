package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.UsuarioDTO;

public class UsuarioMapper {

    private UsuarioMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        if(usuarioDTO.getNome() != null)
            usuario.setNome(usuarioDTO.getNome());
        if(usuarioDTO.getSobrenome() != null)
            usuario.setSobrenome(usuarioDTO.getSobrenome());
        if(usuarioDTO.getLogin() != null)
            usuario.setLogin(usuarioDTO.getLogin());
        if(usuarioDTO.getSenha() != null)
            usuario.setSenha(usuarioDTO.getSenha());
        if(usuarioDTO.getEmail() != null)
            usuario.setEmail(usuarioDTO.getEmail());
        if(usuarioDTO.getDataNascimento() != null)
            usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        if(usuarioDTO.getCnpj() != null)
            usuario.setCnpj(usuarioDTO.getCnpj());
        if(usuarioDTO.getCpf() != null)
            usuario.setCpf(usuarioDTO.getCpf());
        if(usuarioDTO.getTelefone() != null)
            usuario.setTelefone(usuarioDTO.getTelefone());
        if(usuarioDTO.getPerfil() != null)
            usuario.setPerfil(usuarioDTO.getPerfil());
        if(usuarioDTO.getAceitouTermos() != null)
            usuario.setAceitouTermos(usuarioDTO.getAceitouTermos());

        return usuario;
    }

    public static UsuarioDTO usuarioToUsuarioDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        if(usuario.getId() != null)
            usuarioDTO.setId(usuario.getId());
        if(usuario.getNome() != null)
            usuarioDTO.setNome(usuario.getNome());
        if(usuario.getSobrenome() != null)
            usuarioDTO.setSobrenome(usuario.getSobrenome());
        if(usuario.getLogin() != null)
            usuarioDTO.setLogin(usuario.getLogin());
        if(usuario.getSenha() != null)
            usuarioDTO.setSenha(usuario.getSenha());
        if(usuario.getEmail() != null)
            usuarioDTO.setEmail(usuario.getEmail());
        if(usuario.getDataNascimento() != null)
            usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        if(usuario.getCnpj() != null)
            usuarioDTO.setCnpj(usuario.getCnpj());
        if(usuario.getCpf() != null)
            usuarioDTO.setCpf(usuario.getCpf());
        if(usuario.getTelefone() != null)
            usuarioDTO.setTelefone(usuario.getTelefone());
        if(usuario.getPerfil() != null)
            usuarioDTO.setPerfil(usuario.getPerfil());
        if(usuario.getAceitouTermos() != null)
            usuarioDTO.setAceitouTermos(usuario.getAceitouTermos());

        return usuarioDTO;
    }
}
