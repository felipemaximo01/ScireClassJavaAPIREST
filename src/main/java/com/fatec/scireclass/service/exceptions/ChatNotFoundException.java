package com.fatec.scireclass.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String ex){
        super(ex);
    }
    
}
