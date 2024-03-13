package com.fatec.scireclass.model;

import com.lowagie.text.Document;

public class CertificadoDocument {
    private Document certificado;
    private String caminho;
    
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
