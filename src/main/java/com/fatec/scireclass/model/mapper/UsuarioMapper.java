package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.UsuarioDTO;

public class UsuarioMapper {

    private UsuarioMapper(){
        throw new IllegalStateException("Utility class");
    }

    public static Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTO.getNome());
        usuario.setSobrenome(usuarioDTO.getSobrenome());
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        if(usuarioDTO.getCnpj() != null)
            usuario.setCnpj(usuarioDTO.getCnpj());
        if(usuarioDTO.getCpf() != null)
            usuario.setCpf(usuarioDTO.getCpf());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setPerfil(usuarioDTO.getPerfil());

        return usuario;
    }

    public static UsuarioDTO usuarioToUsuarioDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        if(usuario.getCnpj() != null)
            usuarioDTO.setCnpj(usuario.getCnpj());
        if(usuario.getCpf() != null)
            usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setPerfil(usuario.getPerfil());

        return usuarioDTO;
    }
}
