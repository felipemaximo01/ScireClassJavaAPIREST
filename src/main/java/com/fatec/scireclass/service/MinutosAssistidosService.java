package com.fatec.scireclass.service;

import com.fatec.scireclass.model.dto.MinutosAssistidosDTO;
import org.springframework.stereotype.Service;

@Service
public interface MinutosAssistidosService {

    MinutosAssistidosDTO findByAlunoId(String alunoId);

    MinutosAssistidosDTO save(MinutosAssistidosDTO minutosAssistidosDTO, String alunoId);
}
