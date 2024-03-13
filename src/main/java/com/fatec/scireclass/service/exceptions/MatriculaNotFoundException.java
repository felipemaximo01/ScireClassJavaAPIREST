package com.fatec.scireclass.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatriculaNotFoundException extends RuntimeException {
    public MatriculaNotFoundException(String ex){
        super(ex);
    }
}