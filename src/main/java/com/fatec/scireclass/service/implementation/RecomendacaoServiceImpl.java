package com.fatec.scireclass.service.implementation;

import com.fatec.scireclass.model.*;
import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.model.mapper.CursoMapper;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.MatriculaRepository;
import com.fatec.scireclass.repository.UserViewRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.RecomendacaoService;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.apache.catalina.User;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacaoServiceImpl implements RecomendacaoService {

    private static final int PESOMATRICULA = 3;
    private static final int PESOFAVORITO = 2;
    private static final int PESOACESSO = 1;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserViewRepository userViewRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Override
    public List<CursoDTO> cursosRecomendados(String usuarioID) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioID);

        if(usuario == null)
            throw new ResourceNotFoundException("Usuário não encontrado");

        Set<CursoDTO> cursosRecomendados = new HashSet<>();

        List<Matricula> matriculas = matriculaRepository.findAllByAluno_Id(usuarioID);

        List<Curso> cursosMatriculados = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            cursosMatriculados.add(matricula.getCurso());
        }

        List<Curso> cursosFavoritos = usuario.getCursoFavorito();

        List<UserView> userViews = userViewRepository.findUserViewByUsuario(usuario.getId());

        List<Curso> cursoVistos = new ArrayList<>();
        for(UserView userView : userViews){
            cursoVistos.add(cursoRepository.findCursoById(userView.getCurso()));
        }

        Set<Categoria> categorias = new HashSet<>();
        for(Curso curso : cursosMatriculados){
            if(!categorias.contains(curso.getCategoria()))
                categorias.add(curso.getCategoria());
        }

        for(Curso curso : cursosFavoritos){
            if(!categorias.contains(curso.getCategoria()))
                categorias.add(curso.getCategoria());
        }

        for(Curso curso : cursoVistos){
            if(!categorias.contains(curso.getCategoria()))
                categorias.add(curso.getCategoria());
        }

        Set<String> categoriasId = categorias.stream()
                .map(Categoria::getId)
                .collect(Collectors.toSet());

        Set<String> cursosMatriculaId = cursosMatriculados.stream()
                .map(Curso::getId)
                .collect(Collectors.toSet());


        if(userViews.isEmpty() && cursosFavoritos.isEmpty() && cursosMatriculados.isEmpty()){
            if(usuario.getAlunoPreferenciaInicial() != null){
                List<Curso> cursos = cursoRepository.findByCategoria(usuario.getAlunoPreferenciaInicial());
                for(Curso curso : cursos){
                    cursosRecomendados.add(CursoMapper.cursoToCursoDTO(curso));
                }
            }
        }else{
            Map<String, Integer> perfilUsuario = new HashMap<>();
            for(Curso curso : cursosMatriculados){
                perfilUsuario.put(curso.getId(), perfilUsuario.getOrDefault(curso.getId(), 0) + PESOMATRICULA);
            }
            for(Curso curso : cursosFavoritos){
                perfilUsuario.put(curso.getId(), perfilUsuario.getOrDefault(curso.getId(), 0) + PESOFAVORITO);
            }
            for(Curso curso : cursoVistos){
                perfilUsuario.put(curso.getId(), perfilUsuario.getOrDefault(curso.getId(), 0) + PESOACESSO);
            }

            Map<String, Map<String, Integer>> similarityMatrix = buildSimilarityMatrix(categorias);

            Map<String, Double> courseScores = new HashMap<>();

            for(String cursoId : perfilUsuario.keySet()){
                int userCourseWeight = perfilUsuario.get(cursoId);

                Map<String, Integer> similarCourses = similarityMatrix.getOrDefault(cursoId, new HashMap<>());

                for(Map.Entry<String, Integer> entry : similarCourses.entrySet()){
                    String similarCursoId = entry.getKey();
                    int similarityScore = entry.getValue();

                    double weightedScore = userCourseWeight * similarityScore;
                    courseScores.put(similarCursoId, courseScores.getOrDefault(similarCursoId, 0.0) + weightedScore);
                }
            }
            List<Curso> cursosPorScore = courseScores.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .map(entry -> cursoRepository.findCursoById(entry.getKey()))
                    .filter(Objects::nonNull)
                    .filter(course -> !cursosMatriculaId.contains(course.getId()))
                    .filter(course -> categoriasId.contains(course.getCategoria().getId()))
                    .limit(10)
                    .collect(Collectors.toList());

            for(Curso curso : cursosPorScore){
                cursosRecomendados.add(CursoMapper.cursoToCursoDTO(curso));
            }
        }

        return cursosRecomendados.stream().toList();
    }

    private Map<String, Map<String, Integer>> buildSimilarityMatrix(Set<Categoria> usuarioCategorias) {
        Map<String, Map<String, Integer>> similarityMatrix = new HashMap<>();

        List<Matricula> matriculas = matriculaRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findUsuariosWithFavoritos();
        List<Curso> favoritos = new ArrayList<>();
        for(Usuario usuario : usuarios){
            favoritos.addAll(usuario.getCursoFavorito());
        }

        Set<String> categoriasId = usuarioCategorias.stream()
                .map(Categoria::getId)
                .collect(Collectors.toSet());

        List<UserView> views = userViewRepository.findAll();

        for(Matricula matricula : matriculas){
            String cursoId = matricula.getCurso().getId();
            Curso curso = cursoRepository.findCursoById(cursoId);
            if(curso != null && categoriasId.contains(curso.getCategoria().getId())){
                similarityMatrix.putIfAbsent(cursoId, new HashMap<>());
                for(Matricula outraMatricula : matriculas){
                    if(!cursoId.equals(outraMatricula.getCurso().getId())
                            && curso.getCategoria().getId().equals(outraMatricula.getCurso().getCategoria().getId())){
                        similarityMatrix.get(cursoId).merge(outraMatricula.getCurso().getId(), PESOMATRICULA, Integer::sum);
                    }
                }
            }
        }

        for(Curso curso : favoritos){
            String cursoId = curso.getId();
            if(categoriasId.contains(curso.getCategoria().getId())){
                similarityMatrix.putIfAbsent(cursoId, new HashMap<>());
                for(Curso outroFavorito : favoritos){
                    if(!cursoId.equals(outroFavorito.getId())
                            && curso.getCategoria().getId().equals(outroFavorito.getCategoria().getId())){
                        similarityMatrix.get(cursoId).merge(outroFavorito.getId(), PESOFAVORITO, Integer::sum);
                    }
                }
            }
        }

        for(UserView userView : views){
            String cursoId = userView.getCurso();
            Curso curso = cursoRepository.findCursoById(cursoId);
            if(curso != null && categoriasId.contains(curso.getCategoria().getId())){
                similarityMatrix.putIfAbsent(cursoId, new HashMap<>());
                for(UserView outroView : views){
                    Curso outroCurso = cursoRepository.findCursoById(outroView.getCurso());
                    if(!cursoId.equals(outroView.getCurso()) && curso.getCategoria().getId().equals(outroCurso.getCategoria().getId())){
                        similarityMatrix.get(cursoId).merge(outroView.getCurso(), PESOMATRICULA, Integer::sum);
                    }
                }
            }
        }

        return similarityMatrix;
    }
}
