package com.fatec.scireclass.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.model.mapper.CursoMapper;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ImagemService imagemService;
    @Autowired
    private CategoriaService categoriaService;

    @Override
    public CursoDTO cadastrarCurso(CursoDTO cursoDTO, MultipartFile file) throws GeneralSecurityException, IOException {
        
        Curso curso = CursoMapper.cursoDTOToCurso(cursoDTO);
        curso = cursoRepository.save(curso);
        imagemService.addImage(curso.getNome() + " Imagem", curso.getId(), file);
        return CursoMapper.cursoToCursoDTO(curso);
    }

    @Override
    public List<CursoDTO> encontrarDesc(String desc) {
        List<Curso> cursos = cursoRepository.findByDescricaoLikeIgnoreCase(desc);
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }
        return cursoDTOs;
    }

    @Override
    public List<CursoDTO> encontrarNome(String nome) {
        List<Curso> cursos = cursoRepository.findByDescricaoLikeIgnoreCase(nome);
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }
        return cursoDTOs;
    }

    @Override
    public void deletaCurso(String id) {
        this.cursoRepository.deleteById(id);
    }

    @Override
    public CursoDTO encontrarId(String id) {
        Curso curso = cursoRepository.findCursoById(id);
        CursoDTO cursoDTO = new CursoDTO();
        if(curso != null){
            cursoDTO = CursoMapper.cursoToCursoDTO(curso);
        }
        return cursoDTO;
    }

    @Override
    public List<CursoDTO> topCurso(){
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }
        return cursoDTOs;
    }

    @Override
    public List<CursoDTO> cursodaCategoria(String categoriaId) {
        Categoria categoria = categoriaService.categoriaPorId(categoriaId);
        List<Curso> cursos = cursoRepository.findByCategoria(categoria);
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }

        return cursoDTOs;
    }

    @Override
    public CursoDTO alterarDadosCurso(CursoDTO cursoDTO, String cursoId) {
        Curso curso = this.cursoRepository.findCursoById(cursoId);
        if(curso.getId() == null)
            throw new CursoNotFoundException("Não foi possivel encontrar o curso com o ID: " + cursoId);
        if(cursoDTO.getNome() != null){
            curso.setNome(cursoDTO.getNome());
        }
        if(cursoDTO.getDescricao() != null){
            curso.setDescricao(cursoDTO.getDescricao());
        }
        if(cursoDTO.getLink() != null){
            curso.setLink(cursoDTO.getLink());
        }
        if(cursoDTO.getTelefone() != null){
            curso.setTelefone(cursoDTO.getTelefone());
        }
        if(cursoDTO.getDuracao() != null){
            curso.setDuracao(cursoDTO.getDuracao());
        }
        if(cursoDTO.getEmail() != null){
            curso.setEmail(cursoDTO.getEmail());
        }
        if(cursoDTO.getValor() != null){
            curso.setValor(cursoDTO.getValor());
        }
        if(cursoDTO.getVagas() != null){
            curso.setVagas(cursoDTO.getVagas());
        }

        curso = cursoRepository.save(curso);

        return CursoMapper.cursoToCursoDTO(curso);
    }

    @Override
    public List<CursoDTO> cursosFavoritos(Usuario usuario) {
        List<String> cursosId = new ArrayList<>();
        if(!usuario.getCursoFavorito().isEmpty()){
            for(int i = 0; i < usuario.getCursoFavorito().size(); i++) {
                cursosId.add(usuario.getCursoFavorito().get(i).getId());
            }
        }

        List<Curso> cursos = cursoRepository.findAllById(cursosId);
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }
        return cursoDTOs;
    }

    @Override
    public List<CursoDTO> cursosCriador(Usuario usuario){
        List<Curso> cursos = cursoRepository.findAllByCriador_Id(usuario.getId());
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = CursoMapper.cursoToCursoDTO(curso);
            cursoDTOs.add(cursoDTO);
        }

        return cursoDTOs;
    }

    @Override
    public Boolean excluirCurso(String cursoId, Usuario usuario) {
        Curso curso =  cursoRepository.findCursoById(cursoId);
        if(curso == null)
            throw new CursoNotFoundException("Não foi possivel encontrar o curso com o ID: " + cursoId);
        if(curso.getCriador().getId().equals(usuario.getId())){
            this.cursoRepository.deleteById(curso.getId());
            return true;
        }
        return false;
    }

}
