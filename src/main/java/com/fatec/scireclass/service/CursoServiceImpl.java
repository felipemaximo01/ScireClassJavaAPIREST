package com.fatec.scireclass.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.CadastroCursoDTO;
import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.model.mapper.CursoMapper;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.EnderecoRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.exceptions.CategoriaNotFoundException;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ImagemService imagemService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public CursoDTO cadastrarCurso(CadastroCursoDTO cadastroCursoDTO, String criadorId ,MultipartFile file) throws GeneralSecurityException, IOException {
        Usuario usuario = usuarioRepository.findById(criadorId).get();
        if(usuario == null)
            throw new UsuarioNotFoundException("não foi encontrado o usuário com ID: " + criadorId);
        Categoria categoria = categoriaService.categoriaPorId(cadastroCursoDTO.getCategoriaDTO().getId());
        if(categoria == null)
            throw new CategoriaNotFoundException("não foi encontrada a categoria com ID: " + cadastroCursoDTO.getCategoriaDTO().getId());
        Curso curso = CursoMapper.cursoDTOToCurso(cadastroCursoDTO.getCursoDTO());
        Endereco endereco = enderecoRepository.save(EnderecoMapper.enderecoDTOToEndereco(cadastroCursoDTO.getEnderecoDTO()));
        curso.setCriador(usuario);
        curso.setCategoria(categoria);
        curso = cursoRepository.save(curso);
        curso.setEndereco(endereco);
        endereco.setCurso(curso);
        enderecoRepository.save(endereco);
        curso = cursoRepository.save(curso);        
        imagemService.addImage(curso.getNome() + UUID.randomUUID().toString(), curso.getId(), file);
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
