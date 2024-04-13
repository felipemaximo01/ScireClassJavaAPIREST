package com.fatec.scireclass.service;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Certificado;

@Service
public interface CertificadoService {
    
    
    public void gerarCabecalho();
    public void gerarCorpo();
    public void gerarRodape();
    public Certificado imprimir();
    
}
