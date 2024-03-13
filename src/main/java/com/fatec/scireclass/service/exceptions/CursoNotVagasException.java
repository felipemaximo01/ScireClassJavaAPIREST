package com.fatec.scireclass.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CursoNotVagasException extends RuntimeException {
    public CursoNotVagasException(String ex){
        super(ex);
    }
}
