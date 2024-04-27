package com.fatec.scireclass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.dto.AulaDTO;

@Service
public interface AulaService {
    List<AulaDTO> getAulas(String cursoId);
    AulaDTO getAula(String aulaId);
    AulaDTO createAula(AulaDTO aulaDTO,String cursoId);
    AulaDTO updateAula(AulaDTO aulaDTO, String aulaId);
    void deleteAula(String aulaId);

}
