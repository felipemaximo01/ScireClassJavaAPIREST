package com.fatec.scireclass.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fatec.scireclass.model.*;
import com.fatec.scireclass.model.dto.MatriculaDTO;
import com.fatec.scireclass.model.dto.VideoDTO;
import com.fatec.scireclass.model.mapper.VideoMapper;
import com.fatec.scireclass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.fatec.scireclass.model.dto.AulaDTO;
import com.fatec.scireclass.model.mapper.AulaMapper;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private MinutosAssistidosRepository minutosAssistidosRepository;

    @Override
    public List<AulaDTO> getAulas(String cursoId) {
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("O Curso de ID: " + cursoId + " não foi encontrado");
        List<Aula> aulas = aulaRepository.findByCurso(curso);
        List<AulaDTO> aulasDTO = new ArrayList<>();
        for (Aula aula : aulas) {
            AulaDTO aulaDTO = AulaMapper.aulaToAulaDTO(aula);
            aulaDTO.setDuracao(aula.getVideo().getDurationInMinutes());
            aulasDTO.add(aulaDTO);
        }
        return aulasDTO;

    }

    @Override
    public AulaDTO getAula(String aulaId) {
        Aula aula = aulaRepository.findAulaById(aulaId);
        if(aula == null)
            throw new ResourceNotFoundException("Não foi encontrada a aula de ID:" + aulaId);
        AulaDTO aulaDTO = AulaMapper.aulaToAulaDTO(aula);
        aulaDTO.setDuracao(aula.getVideo().getDurationInMinutes());
        return aulaDTO;
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
        aula.setOrdem(curso.getAulas().size() - 1);
        aula = aulaRepository.save(aula);
        cursoRepository.save(curso);
        return AulaMapper.aulaToAulaDTO(aula);

    }

    @Override
    public AulaDTO updateAula(AulaDTO aulaDTO, String aulaId) {
        Aula aula = aulaRepository.findAulaById(aulaId);
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
        Aula aula = aulaRepository.findAulaById(aulaId);
        if(aula == null)
            throw new ResourceNotFoundException("Não foi encontrada a aula de ID:" + aulaId);
        aulaRepository.delete(aula);
    }

    @Override
    public VideoDTO saveAulasAssistidas(String usuarioId, String aulaId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new ResourceNotFoundException("Usuario não encontrado");
        Aula aula = aulaRepository.findAulaById(aulaId);
        if(aula == null)
            throw new ResourceNotFoundException("Aula não encontrada a aula de ID:" + aulaId);
        Matricula matricula = matriculaRepository.findMatriculaByAlunoAndCurso(usuario,aula.getCurso());
        if(matricula == null)
            throw new ResourceNotFoundException("matricula não encontrada");

        MinutosAssistidos minutosAssistidos = new MinutosAssistidos();
        minutosAssistidos.setAluno(usuario);
        minutosAssistidos.setMinutos(aula.getVideo().getDurationInMinutes());
        minutosAssistidos.setData(new Date());

        minutosAssistidosRepository.save(minutosAssistidos);

        if(matricula.getAulasAssistidas().isEmpty()){
            matricula.getAulasAssistidas().add(aula);
        }else{
            for(Aula aula1 : matricula.getAulasAssistidas()){
                if(aula1.getId().equals(aula.getId())){
                    return VideoMapper.videoToVideoDTO(aula.getVideo());
                }
            }
        }

        matricula.getAulasAssistidas().add(aula);

        matriculaRepository.save(matricula);

        return VideoMapper.videoToVideoDTO(aula.getVideo());
    }

}
