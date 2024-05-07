package com.fatec.scireclass.model.dto;

import com.fatec.scireclass.model.enums.Modalidade;

public class CursoDTO {
    private String id;
    private String nome;
    private String descricao;
    private String link;
    private Integer duracao;
    private String telefone;
    private String email;
    private Double valor;
    private Double avaliacao;
    private Integer vagas;
    private Boolean ativo;
    private Modalidade modalidade;
    private Boolean aceitouTermos;
    private Integer quantidadeAulas;
    private Integer quantidadeAulasAssistidas;
    private Integer minutosTotalCurso;
    private String nomeCriador;
    private String pathThumbnail;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public Integer getDuracao() {
        return duracao;
    }
    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public Integer getVagas() {
        return vagas;
    }
    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    public Modalidade getModalidade() {
        return modalidade;
    }
    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
    public Boolean getAceitouTermos() {
        return aceitouTermos;
    }
    public void setAceitouTermos(Boolean aceitouTermos) {
        this.aceitouTermos = aceitouTermos;
    }

    public Integer getQuantidadeAulas() {
        return quantidadeAulas;
    }

    public void setQuantidadeAulas(Integer quantidadeAulas) {
        this.quantidadeAulas = quantidadeAulas;
    }

    public Integer getQuantidadeAulasAssistidas() {
        return quantidadeAulasAssistidas;
    }

    public void setQuantidadeAulasAssistidas(Integer quantidadeAulasAssistidas) {
        this.quantidadeAulasAssistidas = quantidadeAulasAssistidas;
    }

    public Integer getMinutosTotalCurso() {
        return minutosTotalCurso;
    }

    public void setMinutosTotalCurso(Integer minutosTotalCurso) {
        this.minutosTotalCurso = minutosTotalCurso;
    }

    public String getNomeCriador() {
        return nomeCriador;
    }

    public void setNomeCriador(String nomeCriador) {
        this.nomeCriador = nomeCriador;
    }

    public String getPathThumbnail() {
        return pathThumbnail;
    }

    public void setPathThumbnail(String pathThumbnail) {
        this.pathThumbnail = pathThumbnail;
    }
}
