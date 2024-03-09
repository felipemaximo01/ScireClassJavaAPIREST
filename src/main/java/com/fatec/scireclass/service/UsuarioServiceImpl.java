package com.fatec.scireclass.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.UsuarioRepository;
import com.fatec.scireclass.model.enums.Perfil;
import com.fatec.scireclass.repository.CursoRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@ [A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public Usuario cadastrar(Usuario usuario) {
        if(Boolean.FALSE.equals(encontrarEmail(usuario.getEmail()))) {
            if (validaEmail(usuario)){
                return this.usuarioRepository.save(usuario);
            }else {
                throw new IllegalStateException("Email inválido");
            }
        }else {
            throw new IllegalStateException("Email já cadastrado");
        }
    }

    @Override
    public Usuario login(String email, String senha){
        Usuario usuarioLogin = usuarioRepository.findUsuarioByEmailAndSenha(email,senha);
        if(usuarioLogin != null)
                return usuarioLogin;
        return null;
    }
    @Override
    public Boolean encontrarEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario usuarioPorEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }

    @Override
    public boolean validaEmail(Usuario usuario){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(usuario.getEmail());
        return matcher.matches();
    }

    @Override
    public Usuario favoritaCurso(String idUsuario,String idCurso){
        Curso curso = this.cursoRepository.findCursoById(idCurso);
        List<Curso> cursos = new ArrayList<>();
        Usuario usuario = this.usuarioRepository.findUsuarioById(idUsuario);
        if(usuario.getCursoFavorito() != null) {
            cursos = usuario.getCursoFavorito();
            for(int i =0; i<cursos.size(); i++){
                if(cursos.get(i).getId().equals(idCurso)){
                    return usuario;
                }
            }
            cursos.add(curso);
            usuario.setCursoFavorito(cursos);
        }else{
            cursos.add(curso);
            usuario.setCursoFavorito(cursos);
        }
        this.usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Usuario alteraDados(Usuario usuarioAltera){

        Usuario usuario = this.usuarioRepository.findUsuarioById(usuarioAltera.getId());

        if(usuarioAltera.getLogin() != null){
            usuario.setLogin(usuarioAltera.getLogin());
        }
        if(usuarioAltera.getNome() != null){
            usuario.setNome(usuarioAltera.getNome());
        }
        if(usuarioAltera.getSobrenome() != null){
            usuario.setSobrenome(usuarioAltera.getSobrenome());
        }
        if(usuarioAltera.getTelefone() != null){
            usuario.setTelefone(usuarioAltera.getTelefone());
        }
        if(usuarioAltera.getDataNascimento() != null){
            usuario.setDataNascimento(usuarioAltera.getDataNascimento());
        }
        if(usuarioAltera.getLogin() != null){
            usuario.setSenha(usuarioAltera.getSenha());
        }
        if(usuarioAltera.getEndereco() != null){
            usuario.setEndereco(usuarioAltera.getEndereco());
        }
        if(usuarioAltera.getAtivo() != null){
            usuario.setAtivo(usuarioAltera.getAtivo());
        }
        this.usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Usuario desfavoritaCurso(String idUsuario, String idCurso){
        Usuario usuario = this.usuarioRepository.findUsuarioById(idUsuario);
        List<Curso> cursos = usuario.getCursoFavorito();
        for(int i = 0; i < cursos.size(); i++){
            if(cursos.get(i).getId().equals(idCurso)){
                cursos.remove(i);
            }
        }
        usuario.setCursoFavorito(cursos);
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario encontraUsuario(String email, String senha){
        Usuario usuarioFind = usuarioRepository.findUsuarioByEmailAndSenha(email,senha);
        if(usuarioFind  != null)
            return usuarioFind;
        throw new IllegalStateException("usuário não encontrado");
    }

    @Override
    public Usuario encontrarId(String usuarioId) {
        return this.usuarioRepository.findUsuarioById(usuarioId);
    }

    @Override
    public Boolean favoritado(String usuarioId, String cursoId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        for(int i = 0; i < usuario.getCursoFavorito().size(); i++){
            if(usuario.getCursoFavorito().get(i).getId().equals(cursoId))
                return true;
        }
        return false;
    }

    @Override
    public Boolean excluirUsuario(String usuarioId){
        Usuario usuario = this.usuarioRepository.findUsuarioById(usuarioId);
        if(usuario != null){
            if(usuario.getPerfil() != Perfil.ALUNO){
                this.cursoRepository.deleteCursoByCriador_Id(usuario.getId());
            }
            this.usuarioRepository.deleteById(usuario.getId());
            return true;
        }

        return false;
    }
}
