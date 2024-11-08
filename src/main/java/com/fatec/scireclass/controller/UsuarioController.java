package com.fatec.scireclass.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import com.fatec.scireclass.model.*;
import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.model.enums.Perfil;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.repository.*;
import com.fatec.scireclass.service.implementation.TokenService;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.fatec.scireclass.model.dto.CadastroDTO;
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
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;


    @PostMapping("/save")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody CadastroDTO cadastroDTO, HttpServletRequest request) {

        Usuario usuario = usuarioService.cadastrar(cadastroDTO.getUsuarioDTO(),cadastroDTO.getEnderecoDTO(), cadastroDTO.getCategoriaDTO());
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new CadastroEvent(usuario,request.getLocale(), appUrl));
        return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(usuario), HttpStatus.OK);

    }

    @PostMapping("/automatizaUsuarios")
    public void automatizando(){

        List<Categoria> categorias = categoriaRepository.findAll();

        for(int i = 0; i < 200; i++){
            Faker faker = new Faker(new Locale("pt_BR"));
            Usuario usuario = new Usuario();
            usuario.setNome(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setSenha(faker.internet().password());
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
            usuario.setSenha(encryptedPassword);
            usuario.setCpf(faker.numerify("###.###.###-##"));
            usuario.setPerfil(Perfil.ALUNO);
            usuario.setAtivo(true);
            usuarioRepository.save(usuario);

            String enderecoJson = buscarCep(gerarCepSP());

            Gson gson = new Gson();
            EnderecoDTO endereco = gson.fromJson(enderecoJson, EnderecoDTO.class);
            endereco.setNumero(gerarNumeroResidencial() + "");
            Endereco endereco1 = EnderecoMapper.enderecoDTOToEndereco(endereco);
            enderecoRepository.save(endereco1);
            usuario.setEndereco(endereco1);
            endereco1.setUsuario(usuario);
            enderecoRepository.save(endereco1);
            usuarioRepository.save(usuario);

            Random random = new Random();

            Categoria categoria = categorias.get(random.nextInt(categorias.size()));

            List<Curso> cursos = cursoRepository.findByCategoria(categoria);

            Matricula matricula = new Matricula();

            matricula.setAtivo(true);
            matricula.setCurso(cursos.get(random.nextInt(cursos.size())));
            matricula.setAluno(usuario);
            LocalDateTime localDateTime = LocalDateTime.now();
            matricula.setDataInicio(localDateTime);
            matricula.setNumeroMatricula(i);
            matriculaRepository.save(matricula);
        }
    }

    public static String buscarCep(String cep) {
        String urlString = "https://viacep.com.br/ws/" + cep + "/json/";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha na requisição HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String gerarCepSP() {
        Random random = new Random();

        // Prefixos típicos de CEPs em SP
        int[] prefixosSP = {
                1000, 1100, 1200, 1300, 1400, 1500, 2000, 2100, 2200, 2300,
                2400, 2500, 3000, 3100, 3200, 3300, 3400, 3500, 3600, 3700,
                4000, 4100, 4200, 4300, 4400, 4500, 4600, 5000, 5100, 5200,
                5300, 5400, 5500, 5600, 5700, 5800, 5900, 6000, 6100, 6200,
                6300, 6400, 6500, 6600, 6700, 6800, 6900, 7000, 7100, 7200,
                7300, 7400, 7500, 8000, 8100, 8200, 8300, 8400, 8500, 8600
        };

        int prefixo = prefixosSP[random.nextInt(prefixosSP.length)];
        int sufixo = random.nextInt(1000); // Gera o sufixo de 000 a 999

        // Formatar o CEP como XXXXX-XXX
        return String.format("%05d%03d", prefixo, sufixo);
    }

    // Função para gerar número residencial aleatório
    private static int gerarNumeroResidencial() {
        Random random = new Random();
        return random.nextInt(9999) + 1; // Gera um número residencial entre 1 e 9999
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
        tokenDTO.setId(usuario.getId());
        tokenDTO.setPerfil(usuario.getPerfil());
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @GetMapping("/cursoFavoritado/{usuarioId}/{cursoId}")
    public ResponseEntity<Boolean> cursoFavoritado(@PathVariable String usuarioId, @PathVariable String cursoId){
        return new ResponseEntity<>(this.usuarioService.favoritado(usuarioId,cursoId), HttpStatus.OK);
    }

    @PutMapping("/favorita/{usuarioId}/{cursoId}")
    public ResponseEntity<UsuarioDTO> favorita(@PathVariable String usuarioId, @PathVariable String cursoId){

        if(Boolean.FALSE.equals(this.usuarioService.favoritado(usuarioId,cursoId))){
            return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(this.usuarioService.favoritaCurso(usuarioId,cursoId)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(UsuarioMapper.usuarioToUsuarioDTO(this.usuarioService.desfavoritaCurso(usuarioId,cursoId)), HttpStatus.OK);
        }

    }

    @PutMapping("/v1/edita")
    public ResponseEntity<UsuarioDTO> editaUsuario(@RequestBody CadastroDTO cadastroDTO){
        return new ResponseEntity<>(usuarioService.editarUsuario(cadastroDTO.getUsuarioDTO(), cadastroDTO.getEnderecoDTO()), HttpStatus.OK);
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
