package com.fatec.scireclass.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scireclass.model.Aula;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.dto.AulaDTO;
import com.fatec.scireclass.model.mapper.AulaMapper;
import com.fatec.scireclass.repository.AulaRepository;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<AulaDTO> getAulas(String cursoId) {
        Curso curso = cursoRepository.findById(cursoId).get();
        if(curso == null)
            throw new CursoNotFoundException("O Curso de ID: " + cursoId + " não foi encontrado");
        List<Aula> aulas = aulaRepository.findByCurso(curso);
        List<AulaDTO> aulasDTO = new ArrayList<>();
        for (Aula aula : aulas) {
            aulasDTO.add(AulaMapper.aulaToAulaDTO(aula));
        }
        return aulasDTO;

    }

    @Override
    public AulaDTO getAula(String aulaId) {
        Aula aula = aulaRepository.findById(aulaId).get();
        if(aula == null)
            throw new ResourceNotFoundException("Não foi encontrada a aula de ID:" + aulaId);
        return AulaMapper.aulaToAulaDTO(aula);
    }

    @Override
    public AulaDTO createAula(AulaDTO aulaDTO, String cursoId) {
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new ResourceNotFoundException("Não foi encontrada o curso de ID:" + cursoId);
        Aula aula = AulaMapper.aulaDTOToAula(aulaDTO);
        aula.setCurso(curso);
        aula = aulaRepository.save(aula);
        curso.getAulas().add(aula);
        cursoRepository.save(curso);
        return AulaMapper.aulaToAulaDTO(aula);

    }

    @Override
    public AulaDTO updateAula(AulaDTO aulaDTO, String aulaId) {
        Aula aula = aulaRepository.findById(aulaId).get();
        if(aula == null)
            throw new ResourceNotFoundException("Não foi encontrada a aula de ID:" + aulaId);
        if(aulaDTO.getNome() != null)
            aula.setNome(aulaDTO.getNome());
        if(aulaDTO.getDescricao() != null)
            aula.setDescricao(aulaDTO.getDescricao());
        if(aulaDTO.getOrdem() != null)
            aula.setOrdem(aulaDTO.getOrdem());
        aula = aulaRepository.save(aula);
        return AulaMapper.aulaToAulaDTO(aula);
    }

    @Override
    public void deleteAula(String aulaId) {
        Aula aula = aulaRepository.findById(aulaId).get();
        if(aula == null)
            throw new ResourceNotFoundException("Não foi encontrada a aula de ID:" + aulaId);
        aulaRepository.delete(aula);
    }
    
}
