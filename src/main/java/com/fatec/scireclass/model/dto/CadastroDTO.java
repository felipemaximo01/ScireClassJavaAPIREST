package com.fatec.scireclass.model.dto;

public class CadastroDTO {
    private UsuarioDTO usuarioDTO;
    private EnderecoDTO enderecoDTO;
    
    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }
    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }
    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    
}
