package com.fatec.scireclass.model.dto;

public class CadastroDTO {
    private UsuarioDTO usuarioDTO;
    private EnderecoDTO enderecoDTO;
    private CategoriaDTO categoriaDTO;
    
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

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }

    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
    }
}
