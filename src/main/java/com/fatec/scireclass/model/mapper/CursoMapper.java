package com.fatec.scireclass.model.mapper;

import com.fatec.scireclass.model.Aula;
import com.fatec.scireclass.model.Curso;
import com.fatec.scireclass.model.dto.CursoDTO;

public class CursoMapper {

    private CursoMapper(){
        throw new IllegalStateException("Utility class");
    }
    
    public static Curso cursoDTOToCurso(CursoDTO cursoDTO){
        Curso curso = new Curso();
        if(cursoDTO.getNome() != null)
            curso.setNome(cursoDTO.getNome());
        if(cursoDTO.getDescricao() != null)
            curso.setDescricao(cursoDTO.getDescricao());
        if(cursoDTO.getLink() != null)
            curso.setLink(cursoDTO.getLink());
        if(cursoDTO.getDuracao() != null)
            curso.setDuracao(cursoDTO.getDuracao());
        if(cursoDTO.getTelefone() != null)
            curso.setTelefone(cursoDTO.getTelefone());
        if(cursoDTO.getEmail() != null)
            curso.setEmail(cursoDTO.getEmail());
        if(cursoDTO.getValor() != null)
            curso.setValor(cursoDTO.getValor());
        if(cursoDTO.getVagas() != null)
            curso.setVagas(cursoDTO.getVagas());
        if(cursoDTO.getModalidade() != null)
            curso.setModalidade(cursoDTO.getModalidade());
        if(cursoDTO.getAceitouTermos() != null)
            curso.setAceitouTermos(cursoDTO.getAceitouTermos());
        
        return curso;
    }

    public static CursoDTO cursoToCursoDTO(Curso curso){
        CursoDTO cursoDTO = new CursoDTO();
        if(curso.getId() != null)
            cursoDTO.setId(curso.getId());
        if(curso.getNome() != null)
            cursoDTO.setNome(curso.getNome());
        if(curso.getDescricao() != null)
            cursoDTO.setDescricao(curso.getDescricao());
        if(curso.getLink() != null)
            cursoDTO.setLink(curso.getLink());
        if(curso.getDuracao() != null)
            cursoDTO.setDuracao(curso.getDuracao());
        if(curso.getTelefone() != null)
            cursoDTO.setTelefone(curso.getTelefone());
        if(curso.getEmail() != null)
            cursoDTO.setEmail(curso.getEmail());
        if(curso.getValor() != null)
            cursoDTO.setValor(curso.getValor());
        if(curso.getVagas() != null)
            cursoDTO.setVagas(curso.getVagas());
        if(curso.getModalidade() != null)
            cursoDTO.setModalidade(curso.getModalidade());
        if(curso.getAceitouTermos() != null)
            cursoDTO.setAceitouTermos(curso.getAceitouTermos());
        if(curso.getAulas() != null) {
            if (curso.getAulas().isEmpty()) {
                cursoDTO.setQuantidadeAulas(0);
            } else {
                cursoDTO.setQuantidadeAulas(curso.getAulas().size());
                cursoDTO.setMinutosTotalCurso(0);
                for (Aula aula : curso.getAulas()) {
                    cursoDTO.setMinutosTotalCurso(cursoDTO.getMinutosTotalCurso() + aula.getVideo().getDurationInMinutes());
                }
            }
        }
        if(curso.getCriador() != null && curso.getCriador().getNome() != null)
            cursoDTO.setNomeCriador(curso.getCriador().getNome());
        if(curso.getImagem() != null)
            cursoDTO.setPathThumbnail(curso.getImagem().getPath());
        if(!curso.getAvaliacao().isEmpty()){
            Double avaliacao = 0.0;
            for(Double av2 : curso.getAvaliacao()){
                avaliacao += av2;
            }
            cursoDTO.setAvaliacao(avaliacao/curso.getAvaliacao().size());
        }else{
            cursoDTO.setAvaliacao(0.0);
        }


        return cursoDTO;
    }
}
