package com.fatec.scireclass.model.dto;

public class CadastroCursoDTO {
    private CursoDTO cursoDTO;
    private EnderecoDTO enderecoDTO;
    private CategoriaDTO categoriaDTO;
    private String imageBase64;
    
    public CursoDTO getCursoDTO() {
        return cursoDTO;
    }
    public void setCursoDTO(CursoDTO cursoDTO) {
        this.cursoDTO = cursoDTO;
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
    public String getImageBase64() {
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
