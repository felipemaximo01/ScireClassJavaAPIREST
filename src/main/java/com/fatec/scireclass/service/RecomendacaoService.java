package com.fatec.scireclass.service;

import com.fatec.scireclass.model.dto.CursoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecomendacaoService {
    public List<CursoDTO> cursosRecomendados(String usuarioID);

}
