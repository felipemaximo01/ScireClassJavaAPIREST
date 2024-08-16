package com.fatec.scireclass.model;

import com.lowagie.text.Document;

public class Certificado {
    private String nome;
    private Matricula matricula;
    private Document certificado;
    private String caminho;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Document getCertificado() {
        return certificado;
    }

    public void setCertificado(Document certificado) {
        this.certificado = certificado;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
