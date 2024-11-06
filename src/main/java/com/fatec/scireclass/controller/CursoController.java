package com.fatec.scireclass.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fatec.scireclass.model.Categoria;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.Endereco;
import com.fatec.scireclass.model.mapper.CursoMapper;
import com.fatec.scireclass.model.mapper.EnderecoMapper;
import com.fatec.scireclass.repository.CategoriaRepository;
import com.fatec.scireclass.repository.CursoRepository;
import com.fatec.scireclass.repository.EnderecoRepository;
import com.fatec.scireclass.repository.UsuarioRepository;
import com.google.gson.Gson;

import com.fatec.scireclass.model.dto.CursoFilterDTO;
import com.fatec.scireclass.model.dto.EnderecoDTO;
import com.fatec.scireclass.service.implementation.Base64Service;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.scireclass.model.Usuario;
import com.fatec.scireclass.model.dto.CadastroCursoDTO;
import com.fatec.scireclass.model.dto.CursoDTO;
import com.fatec.scireclass.service.CursoService;
import com.fatec.scireclass.service.UsuarioService;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private UsuarioService usuarioService;

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    private static final String NOTFOUNDUSUARIO = "Não foi encontrado o usuário com o ID: ";
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> findAll() {
        return new ResponseEntity<>(cursoService.getAllCursos(), HttpStatus.OK);
    }

    @PostMapping("/save/{criadorId}")
    public ResponseEntity<CursoDTO> salvarCurso(@RequestBody CadastroCursoDTO cadastroCursoDTO,@PathVariable String criadorId) throws GeneralSecurityException, IOException {
        
        CursoDTO cursoResponse = cursoService.cadastrarCurso(cadastroCursoDTO,criadorId, Base64Service.base64ToInputStream(cadastroCursoDTO.getImageBase64()));

        return new ResponseEntity<>(cursoResponse, HttpStatus.OK);
    }

    @GetMapping("/finddesc/{desc}")
    public ResponseEntity<List<CursoDTO>> buscardeescricao(@PathVariable String desc){
        return new ResponseEntity<>(this.cursoService.encontrarDesc(desc), HttpStatus.OK);
    }

    @GetMapping("/findname/{nome}")
    public ResponseEntity<List<CursoDTO>> buscarNome(@PathVariable String nome){
        return new ResponseEntity<>(this.cursoService.encontrarNome(nome), HttpStatus.OK);
    }

    @GetMapping("/findid/{cursoId}")
    public ResponseEntity<CursoDTO> buscarId(@PathVariable String cursoId){
        CursoDTO cursoResponse = this.cursoService.encontrarId(cursoId);

        if(cursoResponse.getId() ==  null)
            throw new CursoNotFoundException("Não foi possivel encontrar o curso com o ID: " + cursoId);
        return new ResponseEntity<>(cursoResponse, HttpStatus.OK);

    }

    @DeleteMapping ("/delete/{cursoId}")
    public void deletarCurso(@PathVariable String cursoId){
        this.cursoService.deletaCurso(cursoId);
    }

    @GetMapping ("/top")
    public ResponseEntity<List<CursoDTO>> buscarTop(){
        return new ResponseEntity<>(this.cursoService.topCurso(), HttpStatus.OK);
    }

    @GetMapping("/findofcat/{categoriaId}")
    public ResponseEntity<List<CursoDTO>> buscarDaCate(@PathVariable String categoriaId){
        return new ResponseEntity<>(this.cursoService.cursodaCategoria(categoriaId), HttpStatus.OK);
    }

    @PutMapping("/alter/{cursoId}")
    public ResponseEntity<CursoDTO> alterarCurso(@RequestBody CursoDTO cursoDTO, @PathVariable String cursoId) {

        return new ResponseEntity<>(this.cursoService.alterarDadosCurso(cursoDTO,cursoId), HttpStatus.OK);
    }

    @GetMapping("/favoritos/{usuarioId}")
    public ResponseEntity<List<CursoDTO>> cursosFavoritos(@PathVariable String usuarioId){
        Usuario usuario = this.usuarioService.encontrarId(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException(NOTFOUNDUSUARIO+usuarioId);
        
        return new ResponseEntity<>(this.cursoService.cursosFavoritos(usuario), HttpStatus.OK);
 
    }

    @GetMapping("/criador/{usuarioId}")
    public ResponseEntity<List<CursoDTO>> cursosCriador(@PathVariable String usuarioId){
        Usuario usuario = this.usuarioService.encontrarId(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException(NOTFOUNDUSUARIO+usuarioId);

        return new ResponseEntity<>(this.cursoService.cursosCriador(usuario), HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{cursoId}/{usuarioId}")
    public ResponseEntity<String> excluirCurso(@PathVariable String cursoId, @PathVariable String usuarioId){
        Usuario usuario = this.usuarioService.encontrarId(usuarioId);
        if(usuario == null)
            throw new UsuarioNotFoundException(NOTFOUNDUSUARIO+usuarioId);
        
        Boolean excluiu = this.cursoService.excluirCurso(cursoId,usuario);
        if(excluiu != null && excluiu){
            return new ResponseEntity<>("Curso Excluido Com Sucesso", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Falha ao excluir o curso", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CursoDTO>> cursosFilter(@RequestBody CursoFilterDTO cursoFilterDTO){
        return new ResponseEntity<>(this.cursoService.cursosFilter(cursoFilterDTO), HttpStatus.OK);
    }

    @PostMapping("/avaliar")
    public ResponseEntity<CursoDTO> avaliarCurso(@RequestBody CursoDTO cursoDTO){
        return new ResponseEntity<>(cursoService.avaliarCurso(cursoDTO), HttpStatus.OK);
    }

    @PostMapping("/automatizarCurso")
    public void automatizando(@RequestBody List<CursoDTO> cursoDTOs){
        String[] ceps = {"01001000",
                "01310000",
                "01502001",
                "02011000",
                "02512000",
                "03012000",
                "03113000",
                "04001001",
                "04511000",
                "05001000",
                "05512000",
                "06013000",
                "07012001",
                "08011000",
                "08512000",
                "09011000",
                "09512000",
                "10012001",
                "11011000",
                "12012000"
        };
        String[] numero = {"123",
                "456",
                "789",
                "102",
                "987",
                "564",
                "222",
                "345",
                "678",
                "891",
                "135",
                "246",
                "357",
                "468",
                "579",
                "680",
                "791",
                "902",
                "1234",
                "5678"
        };

        int i = 0;
        for(CursoDTO cursoDTO : cursoDTOs) {

            String enderecoJson = buscarCep(ceps[i]);

            if (enderecoJson != null) {
                Gson gson = new Gson();
                EnderecoDTO endereco = gson.fromJson(enderecoJson, EnderecoDTO.class);
                endereco.setNumero(numero[i]);
                Endereco endereco1 = EnderecoMapper.enderecoDTOToEndereco(endereco);
                Curso curso = CursoMapper.cursoDTOToCurso(cursoDTO);
                Categoria categoria = categoriaRepository.findCategoriaByNome(cursoDTO.getCategoria());
                curso.setCategoria(categoria);
                Usuario professor = usuarioRepository.findUsuarioByCategoriaOrCategoria2(cursoDTO.getCategoria(), cursoDTO.getCategoria());
                curso.setCriador(professor);
                curso = cursoRepository.save(curso);
                endereco1 = enderecoRepository.save(endereco1);
                curso.setEndereco(endereco1);
                endereco1.setCurso(curso);
                cursoRepository.save(curso);
                enderecoRepository.save(endereco1);
                i++;
            } else {
                System.out.println("Não foi possível buscar o endereço.");
            }
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

}
