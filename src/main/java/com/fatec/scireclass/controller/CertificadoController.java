package com.fatec.scireclass.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;



import com.fatec.scireclass.model.Certificado;
import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.service.implementation.CertificadoServiceImpl;
import com.fatec.scireclass.service.MatriculaService;
import com.fatec.scireclass.service.exceptions.MatriculaNaoFinalizadaException;
import com.fatec.scireclass.service.exceptions.MatriculaNotFoundException;

import java.io.File;
import java.nio.file.Paths;

@RestController
@RequestMapping("/certificado")
public class CertificadoController {

    @Autowired
    MatriculaService matriculaService;

    @GetMapping("/create/{matriculaId}")
    public ResponseEntity<ByteArrayResource> gerarPDF(@PathVariable String matriculaId) {
        Matricula matricula = matriculaService.buscarMatricula(matriculaId);
        if(matricula == null){
            throw new MatriculaNotFoundException("Não foi possivel encontrar a matricula com o ID: " + matriculaId);
        }
        if(matricula.getDataFim() == null){
            throw new MatriculaNaoFinalizadaException("A matricula de número: " +  matricula.getNumeroMatricula() + " não está finalizada");
        }

        CertificadoServiceImpl certificadoService = new CertificadoServiceImpl(matricula);
        Certificado certificado = certificadoService.imprimir();
        try {
            var file = new File(certificado.getCaminho());
            var path = Paths.get(file.getAbsolutePath());
            var resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                     .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
