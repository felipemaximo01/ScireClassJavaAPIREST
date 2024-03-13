package com.fatec.scireclass.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

@org.springframework.data.mongodb.core.mapping.Document
public class Certificado {
    @Id
    private String id;
    private String nome;
    @DBRef
    private Matricula matricula;
    @Transient
    private CertificadoDocument document;
    private String caminho;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Matricula getMatricula() {
        return matricula;
    }
    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
    public CertificadoDocument getDocument() {
        return document;
    }
    public void setDocument(CertificadoDocument document) {
        this.document = document;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
