package com.fatec.scireclass.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fatec.scireclass.model.enums.Modalidade;

@Document
public class Curso {
    
    @Id
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
    
    @DBRef
    private Usuario criador;
    @DBRef
    private List<Matricula> matriculas = new ArrayList<>();
    @DBRef
    private Endereco endereco;
    @DBRef
    private List<Comentario> comentarios = new ArrayList<>();
    @DBRef
    private Imagem imagem;
    @DBRef
    private Categoria categoria;
    @DBRef
    private List<Aula> aulas = new ArrayList<>();

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
    public Usuario getCriador() {
        return criador;
    }
    public void setCriador(Usuario criador) {
        this.criador = criador;
    }
    public List<Matricula> getMatriculas() {
        return matriculas;
    }
    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public List<Comentario> getComentarios() {
        return comentarios;
    }
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    public Imagem getImagem() {
        return imagem;
    }
    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
    public List<Aula> getAulas() {
        return aulas;
    }
    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }
    
    
}
