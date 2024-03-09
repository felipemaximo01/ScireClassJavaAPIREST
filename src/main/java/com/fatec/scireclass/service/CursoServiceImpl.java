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
import com.fatec.scireclass.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ImagemService imagemService;

    @Override
    public Curso cadastrarCurso(Curso curso, MultipartFile file) throws GeneralSecurityException, IOException {
        Curso cursoSalvo = cursoRepository.save(curso);
        imagemService.addImage(curso.getNome() + "Imagem", cursoSalvo, file);
        return cursoSalvo;
    }

    @Override
    public List<Curso> encontrarDesc(String desc) {
        return this.cursoRepository.findByDescricaoLikeIgnoreCase(desc);
    }

    @Override
    public List<Curso> encontrarNome(String nome) {
        return this.cursoRepository.findByNomeLikeIgnoreCase(nome);
    }

    @Override
    public void deletaCurso(String id) {
        this.cursoRepository.deleteById(id);
    }

    @Override
    public Curso encontrarId(String id) {
        return this.cursoRepository.findCursoById(id);
    }

    @Override
    public List<Curso> topCurso(){
        return this.cursoRepository.findAll();
    }

    @Override
    public List<Curso> cursodaCategoria(Categoria categoria) {
        return this.cursoRepository.findByCategoria(categoria);
    }

    @Override
    public Curso alterarDadosCurso(Curso cursoAlterar) {
        Curso curso = this.cursoRepository.findCursoById(cursoAlterar.getId());
        if(cursoAlterar.getNome() != null){
            curso.setNome(cursoAlterar.getNome());
        }
        if(cursoAlterar.getDescricao() != null){
            curso.setDescricao(cursoAlterar.getDescricao());
        }
        if(cursoAlterar.getLink() != null){
            curso.setLink(cursoAlterar.getLink());
        }
        if(cursoAlterar.getTelefone() != null){
            curso.setTelefone(cursoAlterar.getTelefone());
        }
        if(cursoAlterar.getCategoria() != null){
            curso.setCategoria(cursoAlterar.getCategoria());
        }
        if(cursoAlterar.getDuracao() != null){
            curso.setDuracao(cursoAlterar.getDuracao());
        }
        if(cursoAlterar.getEmail() != null){
            curso.setEmail(cursoAlterar.getEmail());
        }
        if(cursoAlterar.getValor() != null){
            curso.setValor(cursoAlterar.getValor());
        }
        if(cursoAlterar.getVagas() != null){
            curso.setVagas(cursoAlterar.getVagas());
        }
        return  this.cursoRepository.save(curso);
    }

    @Override
    public List<Curso> cursosFavoritos(Usuario usuario) {
        List<String> cursosId = new ArrayList<>();
        if(!usuario.getCursoFavorito().isEmpty()){
            for(int i = 0; i < usuario.getCursoFavorito().size(); i++) {
                cursosId.add(usuario.getCursoFavorito().get(i).getId());
            }
        }
        return this.cursoRepository.findAllById(cursosId);
    }

    @Override
    public List<Curso> cursosCriador(String usuarioId){
        return this.cursoRepository.findAllByCriador_Id(usuarioId);
    }

    @Override
    public Boolean excluirCurso(String cursoId, String usuarioId) {
        Curso curso = this.cursoRepository.findCursoById(cursoId);
        if(curso != null && curso.getCriador().getId().equals(usuarioId)){
            this.cursoRepository.deleteById(curso.getId());
            return true;
        }
        return false;
    }

}
