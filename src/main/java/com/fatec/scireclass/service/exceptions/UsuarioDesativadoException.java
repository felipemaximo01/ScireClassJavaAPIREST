package com.fatec.scireclass.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioDesativadoException extends RuntimeException {
    public UsuarioDesativadoException(String ex){
        super(ex);
    }
}
