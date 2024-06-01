package com.fatec.scireclass.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.fatec.scireclass.model.dto.CursoFilterDTO;
import com.fatec.scireclass.service.Base64Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    private static final String NOTFOUNDUSUARIO = "Não foi encontrado o usuário com o ID: ";

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
}
