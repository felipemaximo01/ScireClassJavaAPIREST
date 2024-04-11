package com.fatec.scireclass.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fatec.scireclass.model.TokenSenhaReset;
import com.fatec.scireclass.model.TokenVerificacao;
import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.TokenDTO;
import com.fatec.scireclass.model.dto.UsuarioDTO;
import com.fatec.scireclass.model.mapper.UsuarioMapper;
import com.fatec.scireclass.service.TokenSenhaResetService;
import com.fatec.scireclass.service.TokenVerificacaoService;
import com.fatec.scireclass.service.UsuarioService;
import com.fatec.scireclass.service.eventlistener.cadastro.CadastroEvent;
import com.fatec.scireclass.service.eventlistener.senhareset.SenhaResetEvent;
import com.fatec.scireclass.service.exceptions.TokenInvalidoException;
import com.fatec.scireclass.service.exceptions.TokenNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioDesativadoException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private TokenVerificacaoService tokenVerificacaoService;
    @Autowired
    private TokenSenhaResetService tokenSenhaResetService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private com.fatec.scireclass.service.TokenService tokenService;


    @PostMapping("/save")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO, HttpServletRequest request) {

        Usuario usuario = usuarioService.cadastrar(usuarioDTO);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new CadastroEvent(usuario,request.getLocale(), appUrl));
        return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(usuario), HttpStatus.OK);

    }

    @PutMapping("/confirmarCadastro")
    public ResponseEntity<UsuarioDTO> confirmarCadastro(WebRequest request, @RequestParam("token") String token){

        TokenVerificacao tokenVerificacao = tokenVerificacaoService.getTokenVerificacao(token);
        if(tokenVerificacao == null){
            throw new TokenNotFoundException("O token: " + token + " não foi encontrado");
        }
        Usuario usuario = tokenVerificacao.getUsuario();
        Calendar cal = Calendar.getInstance();
        if((tokenVerificacao.getExpiracaoData().getTime() - cal.getTime().getTime()) <= 0){
            throw new TokenInvalidoException("O token: " + token + " foi expirado");
        }
        usuario.setAtivo(true);
        usuario = usuarioService.salvaUsuario(usuario);
        return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(usuario), HttpStatus.OK);
    }

    @GetMapping("/findbyid/{usuarioId}")
    public ResponseEntity<UsuarioDTO> buscarId(@PathVariable String usuarioId){
        Usuario usuario = this.usuarioService.encontrarId(usuarioId);

        if(usuario !=  null)
            return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(usuario), HttpStatus.OK);
        else{
            throw new UsuarioNotFoundException("O usuário com ID: " + usuarioId + " não foi encontrado");
        }
    }

    @GetMapping("/resetSenha/{email}")
    public ResponseEntity<String> resetSenha(HttpServletRequest request,@PathVariable String email){
            if(Boolean.TRUE.equals(usuarioService.encontrarEmail(email))){
                Usuario usuario = usuarioService.usuarioPorEmail(email);
                eventPublisher.publishEvent(new SenhaResetEvent(usuario,request.getLocale(), request.getContextPath()));
                return new ResponseEntity<>("Verifique a sua caixa de entrada", HttpStatus.OK);
            }else{
                throw new UsuarioNotFoundException("O usuário com email: " + email + " não foi encontrado");
            }
    }

    @GetMapping("/reenviarVerificacao")
    public ResponseEntity<String> reenviarToken(HttpServletRequest request, String email, String senha){
        Usuario usuario = usuarioService.encontraUsuario(email, senha);
        if(usuario == null)
            throw new UsuarioNotFoundException("O usuário com email: " + email + " não foi encontrado");
        tokenVerificacaoService.deletaTokenVerificacao(usuario.getId());
        eventPublisher.publishEvent(new CadastroEvent(usuario,request.getLocale(), request.getContextPath()));
        return new ResponseEntity<>("Verifique a sua caixa de entrada", HttpStatus.OK);
    }

    @PutMapping("/mudarSenha/{token}")
    public ResponseEntity<UsuarioDTO> salvaSenha(@PathVariable("token") String token, @RequestBody String senha){
        TokenSenhaReset tokenSenhaReset = tokenSenhaResetService.getTokenSenha(token);
        Calendar cal = Calendar.getInstance();
        if(tokenSenhaReset == null)
            throw new TokenNotFoundException("O token: " + token + " não foi encontrado");
        
        if((tokenSenhaReset.getExpiracaoData().getTime() - cal.getTime().getTime()) <= 0)
            throw new TokenInvalidoException("O token: " + token + " foi expirado");
        
        return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(usuarioService.mudarSenha(tokenSenhaReset,senha)), HttpStatus.OK);
    }

    @PutMapping("/altera/{usuarioId}")
    public ResponseEntity<UsuarioDTO> alteraUsuario(@RequestBody UsuarioDTO usuarioDTO, @PathVariable String usuarioId) {
        return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(this.usuarioService.alteraDados(usuarioDTO, usuarioId)), HttpStatus.OK);
    }

    @GetMapping("/login/{email}/{senha}")
    public ResponseEntity<TokenDTO> login(@PathVariable String email, @PathVariable String senha) {

        Usuario usuario = usuarioService.login(email, senha);

        if(usuario == null)
            throw new UsuarioNotFoundException("Não foi possivel realizar o login");
        if(Boolean.FALSE.equals(usuario.getAtivo()))
            throw new UsuarioDesativadoException("O usuário não está ativo");

        var usernamePassword = new UsernamePasswordAuthenticationToken(email, senha);
        
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @GetMapping("/cursoFavoritado/{usuarioId}/{cursoId}")
    public ResponseEntity<Boolean> cursoFavoritado(@PathVariable String usuarioId, @PathVariable String cursoId){
        return new ResponseEntity<>(this.usuarioService.favoritado(usuarioId,cursoId), HttpStatus.OK);
    }

    @PutMapping("/favorita")
    public ResponseEntity<UsuarioDTO> favorita(String usuarioId, String cursoId){

        if(Boolean.FALSE.equals(this.usuarioService.favoritado(usuarioId,cursoId))){
            return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(this.usuarioService.favoritaCurso(usuarioId,cursoId)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(this.usuarioService.desfavoritaCurso(usuarioId,cursoId)), HttpStatus.OK);
        }

    }

    @DeleteMapping("/excluir/{usuarioId}")
    public ResponseEntity<String> excluiUsuario(@PathVariable String usuarioId){

        Boolean excluir = this.usuarioService.excluirUsuario(usuarioId);
        if(Boolean.TRUE.equals(excluir)){
            return new ResponseEntity<>("Usuário com ID: " + usuarioId + " Excluido com sucesso", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Erro ao excluir o usuario", HttpStatus.BAD_REQUEST);
        }


    }
    
}
