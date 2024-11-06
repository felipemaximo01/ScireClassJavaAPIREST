package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.model.MinutosAssistidos;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.MinutosAssistidosDTO;
import com.fatec.scireclass.model.mapper.MinutosAssistidosMapper;
import com.fatec.scireclass.repository.MinutosAssistidosRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.MinutosAssistidosService;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class MinutosAssistidosServiceImpl implements MinutosAssistidosService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MinutosAssistidosRepository minutosAssistidosRepository;

    public MinutosAssistidosServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public MinutosAssistidosDTO findByAlunoId(String alunoId) {
        Usuario usuario = usuarioRepository.findUsuarioById(alunoId);
        if (usuario == null)
            throw new ResourceNotFoundException("Usuário não encontrado");

        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<MinutosAssistidos> minutosAssistidos = minutosAssistidosRepository.findByAlunoAndDataBetween(usuario, startOfDay,endOfDay);
        MinutosAssistidosDTO minutosAssistidosDTO = new MinutosAssistidosDTO();
        Integer totalMinutos = 0;
        if(minutosAssistidos != null && minutosAssistidos.size() > 0) {
            for(MinutosAssistidos minutosAssistido : minutosAssistidos) {
                totalMinutos = totalMinutos + minutosAssistido.getMinutos();
            }
        }
        minutosAssistidosDTO.setMinutos(totalMinutos);
        return minutosAssistidosDTO;

    }

    @Override
    public MinutosAssistidosDTO save(MinutosAssistidosDTO minutosAssistidosDTO, String alunoId) {
        Usuario usuario = usuarioRepository.findUsuarioById(alunoId);
        if (usuario == null)
            throw new ResourceNotFoundException("Usuário não encontrado");

        MinutosAssistidos minutosAssistidos = MinutosAssistidosMapper.minutosAssistidosDTOTominutosAssistidos(minutosAssistidosDTO);
        minutosAssistidos.setAluno(usuario);
        minutosAssistidos.setData(new Date());
        minutosAssistidosRepository.save(minutosAssistidos);

        return MinutosAssistidosMapper.minutosAssistidosTominutosAssistidosDTO(minutosAssistidos);

    }
}
