package com.fatec.scireclass.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.model.dto.MensagemDTO;
import com.fatec.scireclass.model.mapper.CursoMapper;
import com.fatec.scireclass.service.ChatService;
import com.fatec.scireclass.service.MatriculaService;
import com.fatec.scireclass.service.MensagemService;
import com.fatec.scireclass.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Matricula;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.MatriculaDTO;
import com.fatec.scireclass.model.mapper.MatriculaMapper;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.MatriculaRepository;
import com.fatec.scireclass.repository.UsuarioRepository;

@Service
public class MatriculaServiceImpl implements MatriculaService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MensagemService mensagemService;

    @Override
    public MatriculaDTO salvarMatricula(String usuarioId, String cursoId) {
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("Não foi encontrado o curso com ID: " + cursoId);
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        if(curso.getVagas() < 1)
            throw new CursoNotVagasException("Não há vagas para o curso: " + curso.getNome());
        if(Boolean.TRUE.equals(matriculaRepository.existsByAluno_IdAndCursoId(usuario.getId(),curso.getId())))
            throw new MatriculaJaExisteException("O usuário: " + usuario.getNome() + " já está matriculado no curso: " + curso.getNome());

        if(curso.getModalidade().toString().equals("ONLINE")) {
            Matricula matricula = new Matricula();
            matricula.setAluno(usuario);
            matricula.setCurso(curso);
            matricula.setDataInicio(LocalDateTime.now());
            matricula.setNumeroMatricula(gerarNumeroMatricula());
            matricula.setAtivo(true);
            curso.setVagas(curso.getVagas() - 1);
            cursoRepository.save(curso);
            matricula = this.matriculaRepository.save(matricula);
            curso.getMatriculas().add(matricula);
            cursoRepository.save(curso);
            return MatriculaMapper.matriculaToMatriculaDTO(matricula);
        }else if(curso.getModalidade().toString().equals("PRESENCIAL")){
            Matricula matricula = new Matricula();
            matricula.setAluno(usuario);
            matricula.setCurso(curso);
            matricula.setAtivo(false);
            curso.setVagas(curso.getVagas() - 1);
            cursoRepository.save(curso);
            matricula = this.matriculaRepository.save(matricula);
            curso.getMatriculas().add(matricula);
            cursoRepository.save(curso);

            chatService.createChat(usuario.getId(), curso.getCriador().getId(), curso.getId());

            return MatriculaMapper.matriculaToMatriculaDTO(matricula);
        }

        return null;
    }

    @Override
    public Matricula buscarMatricula(String id) {
        return matriculaRepository.getById(id);
    }

    @Override
    public Integer gerarNumeroMatricula() {
        Integer matricula = 0;
        if(matriculaRepository.countAllBy() > 0) {
             matricula = matriculaRepository.countAllBy();
        }
        matricula=matricula+1;
        return matricula;
    }

    @Override
    public MatriculaDTO encerraMatricula(String matriculaId) {
        Matricula matricula = matriculaRepository.getById(matriculaId);
        if(matricula == null)
            throw new MatriculaNotFoundException("Não foi possivel encontrar a matricula com o ID: " + matriculaId);
        matricula.setDataFim(LocalDateTime.now());
        matricula = this.matriculaRepository.save(matricula);
        return MatriculaMapper.matriculaToMatriculaDTO(matricula);

    }

    @Override
    public MatriculaDTO encontramatriculaporAlunoECurso(String usuarioId, String cursoId) {
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("Não foi encontrado o curso com ID: " + cursoId);
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        Matricula matricula = matriculaRepository.findMatriculaByAlunoAndCurso(usuario,curso);
        if(matricula == null)
            throw new MatriculaNotFoundException("Não foi possivel encontrar a matricula!");
        return MatriculaMapper.matriculaToMatriculaDTO(matricula);
    }

    @Override
    public List<MatriculaDTO> encontrarMatriculaFinalizada(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        List<Matricula> matriculas =  this.matriculaRepository.findAllByAluno_Id(usuarioId);
        List<Matricula> matriculasfinalizadas= new ArrayList<>();
        List<MatriculaDTO> matriculasDTO = new ArrayList<>();
        for (int i=0;i<matriculas.size();i++){
            if(matriculas != null && !matriculas.isEmpty() && matriculas.get(i).getDataFim()!=null){
                matriculasfinalizadas.add(matriculas.get(i));
            }
        }
        for (Matricula matricula : matriculasfinalizadas) {
            MatriculaDTO matriculaDTO = MatriculaMapper.matriculaToMatriculaDTO(matricula);
            matriculaDTO.setNomeCurso(matricula.getCurso().getNome());
            matriculaDTO.setNomeProfessor(matricula.getCurso().getCriador().getNome());

            matriculasDTO.add(matriculaDTO);
        }

        return matriculasDTO;
    }

    @Override
    public List<MatriculaDTO> encontrarMatriculas(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        
        List<Matricula> matriculas =  this.matriculaRepository.findAllByAluno_Id(usuarioId);
        List<MatriculaDTO> matriculasDTO = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            MatriculaDTO matriculaDTO = new MatriculaDTO();
            MatriculaMapper.matriculaToMatriculaDTO(matricula);
            matriculasDTO.add(matriculaDTO);
        }

        return matriculasDTO;
    }

    @Override
    public List<CursoDTO> encontrarMatriculasLastFive(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        List<Matricula> matriculas = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("aluno").is(usuario))
                .limit(5)
                .with(Sort.by(Sort.Order.desc("dataInicio")));
        matriculas = mongoTemplate.find(query, Matricula.class);
        List<CursoDTO> cursosDTO = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(matricula.getCurso());
            cursoDTO.setQuantidadeAulasAssistidas(matricula.getAulasAssistidas().size());
            cursosDTO.add(cursoDTO);
        }

        return cursosDTO;
    }

    @Override
    public List<CursoDTO> findCursosByMatricula(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new ResourceNotFoundException("Usuário Não Encontrado");
        List<Matricula> matriculas = new ArrayList<>();
        List<CursoDTO> cursosDTO = new ArrayList<>();
        matriculas = matriculaRepository.findMatriculaByAlunoAndAtivo(usuario, true);
        for (Matricula matricula : matriculas) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(matricula.getCurso());
            cursoDTO.setQuantidadeAulasAssistidas(matricula.getAulasAssistidas().size());
            cursosDTO.add(cursoDTO);
        }
        return cursosDTO;
    }

    @Override
    public MatriculaDTO ativaMatricula(String cursoId, String alunoId, String chatId) {
        Usuario usuario = usuarioRepository.findUsuarioById(alunoId);
        if(usuario == null)
            throw new ResourceNotFoundException("Usuário Não Encontrado");
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("Não foi encontrado o curso com ID: " + cursoId);
        Matricula matricula = matriculaRepository.findMatriculaByAlunoAndCurso(usuario,curso);
        if(matricula == null)
            throw new ResourceNotFoundException("Matricula não encontrada");
        if(matricula.getAtivo()){
            return MatriculaMapper.matriculaToMatriculaDTO(matricula);
        }

        matricula.setAtivo(true);
        matricula.setNumeroMatricula(gerarNumeroMatricula());
        matricula.setDataInicio(LocalDateTime.now());

        matricula = matriculaRepository.save(matricula);

        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setMensagens("Matricula Ativada");

        mensagemService.sendMensagem(mensagemDTO,chatId, curso.getCriador().getId());

        return MatriculaMapper.matriculaToMatriculaDTO(matricula);
    }

    @Override
    public List<MatriculaDTO> encontrarMatriculasPorCriador(String usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        List<Matricula> matriculas = this.matriculaRepository.findAllByCurso_Criador_Id(usuarioId);
        List<MatriculaDTO> matriculasDTO = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            MatriculaDTO matriculaDTO = new MatriculaDTO();
            MatriculaMapper.matriculaToMatriculaDTO(matricula);
            matriculasDTO.add(matriculaDTO);
        }
        return matriculasDTO;
    }

    @Override
    public List<MatriculaDTO> encontrarMatriculasPorCurso(String usuarioId, String cursoId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi encontrado o usuario com ID: " + usuarioId);
        Curso curso = cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("Não foi encontrado o curso com ID: " + cursoId);
        List<Matricula> matriculas = matriculaRepository.findAllByCurso_Criador_IdAndCursoId(usuarioId,cursoId);
        List<MatriculaDTO> matriculasDTO = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            MatriculaDTO matriculaDTO = new MatriculaDTO();
            MatriculaMapper.matriculaToMatriculaDTO(matricula);
            matriculasDTO.add(matriculaDTO);
        }
        return matriculasDTO;
    }

    @Override
    public void excluirMatricula(String matriculaId){
        Matricula matricula = this.matriculaRepository.getById(matriculaId);
        if(matricula == null)
            throw new MatriculaNotFoundException("Não foi possivel encontrar a matricula com o ID: " + matriculaId);

        this.matriculaRepository.deleteById(matricula.getId());
    }
}
