package com.fatec.scireclass.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fatec.scireclass.model.*;
import com.fatec.scireclass.model.dto.CategoriaDTO;
import com.fatec.scireclass.repository.*;
import com.fatec.scireclass.service.UsuarioService;
import com.fatec.scireclass.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.model.dto.UsuarioDTO;
import com.fatec.scireclass.model.enums.Perfil;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.model.mapper.UsuarioMapper;
import com.fatec.scireclass.service.exceptions.EmailInvalidoException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TokenSenhaResetRepository tokenSenhaResetRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Usuario cadastrar(UsuarioDTO usuarioDTO, EnderecoDTO enderecoDTO, CategoriaDTO categoriaDTO) {
        if(Boolean.FALSE.equals(encontrarEmail(usuarioDTO.getEmail()))) {
            if (validaEmail(usuarioDTO)){
                Usuario usuario = UsuarioMapper.usuarioDTOToUsuario(usuarioDTO);
                Endereco endereco = EnderecoMapper.enderecoDTOToEndereco(enderecoDTO);
                Categoria categoria = categoriaRepository.findCategoriaById(categoriaDTO.getId());
                usuario.setAtivo(false);
                String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
                usuario.setSenha(encryptedPassword);
                usuario.setAlunoPreferenciaInicial(categoria);
                endereco = enderecoRepository.save(endereco);
                usuario = this.usuarioRepository.save(usuario);
                usuario.setEndereco(endereco);
                endereco.setUsuario(usuario);
                enderecoRepository.save(endereco);
                return this.usuarioRepository.save(usuario);
            }else {
                throw new EmailInvalidoException("O email: " + usuarioDTO.getEmail() + " não é um email válido");
            }
        }else {
            throw new  EmailInvalidoException("O email: " + usuarioDTO.getEmail() + " já está cadastrado");
        }
    }

    @Override
    public Usuario login(String email, String senha){
        BCryptPasswordEncoder enconde = new BCryptPasswordEncoder();
        Usuario usuarioLogin = usuarioRepository.findUsuarioByEmail(email);
        if(usuarioLogin == null)
                return null;
        if(enconde.matches(senha, usuarioLogin.getSenha()))
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
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
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
        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
        tokenSenhaResetRepository.deleteTokenSenhaByUsuarioEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario salvaUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO editarUsuario(UsuarioDTO usuarioDTO, EnderecoDTO enderecoDTO) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioDTO.getId());
        if(usuario == null)
            throw new ResourceNotFoundException("Usuario não encontrado");

        Endereco endereco = enderecoRepository.findByUsuario(usuario);
        if(endereco == null)
            throw new ResourceNotFoundException("Endereço não encontrado");

        if(usuarioDTO.getNome() != null)
            usuario.setNome(usuarioDTO.getNome());
        if(enderecoDTO.getCep() != null)
            endereco.setCep(enderecoDTO.getCep());
        if(enderecoDTO.getLogradouro() != null)
            endereco.setLogradouro(enderecoDTO.getLogradouro());
        if(enderecoDTO.getComplemento() != null)
            endereco.setComplemento(enderecoDTO.getComplemento());
        if(enderecoDTO.getBairro() != null)
            endereco.setBairro(enderecoDTO.getBairro());
        if(enderecoDTO.getLocalidade() != null)
            endereco.setLocalidade(enderecoDTO.getLocalidade());
        if(enderecoDTO.getUf() != null)
            endereco.setUf(enderecoDTO.getUf());
        if(enderecoDTO.getNumero() != null)
            endereco.setNumero(enderecoDTO.getNumero());

        usuario = usuarioRepository.save(usuario);
        enderecoRepository.save(endereco);

        return UsuarioMapper.usuarioToUsuarioDTO(usuario);
    }
}
