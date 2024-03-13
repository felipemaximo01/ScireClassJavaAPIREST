package com.fatec.scireclass.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.UsuarioDTO;
import com.fatec.scireclass.model.enums.Perfil;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.model.mapper.UsuarioMapper;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.TokenSenhaResetRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.fatec.scireclass.service.exceptions.EmailInvalidoException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TokenSenhaResetRepository tokenSenhaResetRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@ [A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public UsuarioDTO cadastrar(UsuarioDTO usuarioDTO) {
        if(Boolean.FALSE.equals(encontrarEmail(usuarioDTO.getEmail()))) {
            if (validaEmail(usuarioDTO)){
                Usuario usuario = this.usuarioRepository.save(UsuarioMapper.usuarioDTOToUsuario(usuarioDTO));
                return UsuarioMapper.usuarioToUsuarioDTO(usuario);
            }else {
                throw new EmailInvalidoException("O email: " + usuarioDTO.getEmail() + " já está cadastrado");
            }
        }else {
            throw new  EmailInvalidoException("O email: " + usuarioDTO.getEmail() + " já está cadastrado");
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
    public boolean validaEmail(UsuarioDTO usuarioDTO){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(usuarioDTO.getEmail());
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
    public Usuario alteraDados(UsuarioDTO usuarioDTO, String usuarioId){

        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);

        if(usuario == null)
            throw new UsuarioNotFoundException("O usuário com ID: " + usuarioId + " não foi encontrado");

        if(usuarioDTO.getLogin() != null){
            usuario.setLogin(usuarioDTO.getLogin());
        }
        if(usuarioDTO.getNome() != null){
            usuario.setNome(usuarioDTO.getNome());
        }
        if(usuarioDTO.getSobrenome() != null){
            usuario.setSobrenome(usuarioDTO.getSobrenome());
        }
        if(usuarioDTO.getTelefone() != null){
            usuario.setTelefone(usuarioDTO.getTelefone());
        }
        if(usuarioDTO.getDataNascimento() != null){
            usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        }
        if(usuarioDTO.getSenha() != null){
            usuario.setSenha(usuarioDTO.getSenha());
        }
        if(usuarioDTO.getAtivo() != null){
            usuario.setAtivo(usuarioDTO.getAtivo());
        }

        return this.usuarioRepository.save(usuario);
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
        throw new UsuarioNotFoundException("usuário não encontrado");
    }

    @Override
    public Usuario encontrarId(String usuarioId) {
        return this.usuarioRepository.findUsuarioById(usuarioId);
    }

    @Override
    public Boolean favoritado(String usuarioId, String cursoId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException("usuário não encontrado");
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

    @Override
    public Usuario mudarSenha(TokenSenhaReset tokenSenhaReset, String senha) {
        Usuario usuario = usuarioRepository.findUsuarioById(tokenSenhaReset.getUsuario().getId());
        if(usuario == null)
            throw new UsuarioNotFoundException("O usuário não foi encontrado");
        usuario.setSenha(senha);
        tokenSenhaResetRepository.deleteTokenSenhaByUsuarioEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario salvaUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
