package com.fatec.scireclass.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fatec.scireclass.model.enums.Perfil;

@Document
public class Usuario {

    @Id
    private String id;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
    private String email;
    private Date dataNascimento;
    private Double avaliacao;
    private String cnpj;
    private String cpf;
    private String telefone;
    private Integer perfil;
    private Boolean ativo = false;
    @DBRef
    private List<Chat> chat;
    @DBRef
    private List<Curso> cursoFavorito;
    @DBRef
    private List<Certificado> certificados;
    @DBRef
    private List<Matricula> matriculas;
    @DBRef
    private List<Comentario> comentarios;
    @DBRef
    private Endereco endereco;
    
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
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public List<Curso> getCursoFavorito() {
        return cursoFavorito;
    }
    public void setCursoFavorito(List<Curso> cursoFavorito) {
        this.cursoFavorito = cursoFavorito;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public Perfil getPerfil() {
        return Perfil.valueOf(perfil);
    }
    public void setPerfil(Perfil perfil) {
        if (perfil != null) {
            this.perfil = perfil.getCode();
        }
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    public List<Chat> getChat() {
        return chat;
    }
    public void setChat(List<Chat> chat) {
        this.chat = chat;
    }
    public List<Certificado> getCertificados() {
        return certificados;
    }
    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
    }
    public List<Matricula> getMatriculas() {
        return matriculas;
    }
    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    public List<Comentario> getComentarios() {
        return comentarios;
    }
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
