package com.fatec.scireclass.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fatec.scireclass.model.dto.CursoFilterDTO;
import com.fatec.scireclass.repository.CategoriaRepository;
import com.fatec.scireclass.service.CategoriaService;
import com.fatec.scireclass.service.CursoService;
import com.fatec.scireclass.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CursoDTO cadastrarCurso(CadastroCursoDTO cadastroCursoDTO, String criadorId , InputStream inputStream) throws GeneralSecurityException, IOException {
        Usuario usuario = usuarioRepository.findUsuarioById(criadorId);
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
        imagemService.addImage(curso.getNome() + UUID.randomUUID().toString(), curso.getId(), inputStream);
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
        List<Curso> cursos = cursoRepository.findByNomeLikeIgnoreCase(nome);
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

    @Override
    public List<CursoDTO> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            cursoDTOs.add(CursoMapper.cursoToCursoDTO(curso));
        }
        return cursoDTOs;
    }

    @Override
    public List<CursoDTO> cursosFilter(CursoFilterDTO cursoFilterDTO) {
        List<Categoria> categorias = categoriaRepository.findAllById(cursoFilterDTO.getCategoriasID());
        Query query = new Query();
        if(cursoFilterDTO.getNomeCurso() != null && cursoFilterDTO.getNomeCurso() != ""){
            query.addCriteria(Criteria.where("nome").regex(cursoFilterDTO.getNomeCurso(), "i"));
        }
        if(categorias != null && !categorias.isEmpty()){
            query.addCriteria(Criteria.where("categoria").in(categorias));
        }if(cursoFilterDTO.getPrecoMax() != null && cursoFilterDTO.getPrecoMin() != null){
            query.addCriteria(Criteria.where("valor").gte(cursoFilterDTO.getPrecoMin()).lte(cursoFilterDTO.getPrecoMax()));
        }if(cursoFilterDTO.getDuracao() != null && cursoFilterDTO.getDuracao() != ""){
            String[] parts = cursoFilterDTO.getDuracao().split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid duration format");
            }
            int minDuration = Integer.parseInt(parts[0].trim());
            int maxDuration = Integer.parseInt(parts[1].trim());

            query.addCriteria(Criteria.where("duracao").gte(minDuration * 60).lte(maxDuration * 60));
        }

        List<Curso> cursos = mongoTemplate.find(query, Curso.class);
        List<CursoDTO> cursoDTOs = new ArrayList<>();
        for (Curso curso : cursos) {
            cursoDTOs.add(CursoMapper.cursoToCursoDTO(curso));
        }

        return cursoDTOs;
    }

    @Override
    public CursoDTO avaliarCurso(CursoDTO cursoDTO) {
        Curso curso = cursoRepository.findCursoById(cursoDTO.getId());
        if(curso == null)
            throw new CursoNotFoundException("Não foi possivel encontrar o curso com o ID: " + cursoDTO.getId());

       curso.getAvaliacao().add(cursoDTO.getAvaliacao());

       curso = cursoRepository.save(curso);

       return CursoMapper.cursoToCursoDTO(curso);
    }

}
