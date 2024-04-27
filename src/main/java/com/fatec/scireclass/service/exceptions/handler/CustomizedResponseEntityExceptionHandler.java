package com.fatec.scireclass.service.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.azure.core.exception.ResourceNotFoundException;
import com.fatec.scireclass.service.exceptions.CategoriaNotFoundException;
import com.fatec.scireclass.service.exceptions.ChatNotFoundException;
import com.fatec.scireclass.service.exceptions.CursoNotFoundException;
import com.fatec.scireclass.service.exceptions.CursoNotVagasException;
import com.fatec.scireclass.service.exceptions.EmailInvalidoException;
import com.fatec.scireclass.service.exceptions.ExceptionResponse;
import com.fatec.scireclass.service.exceptions.MatriculaNaoFinalizadaException;
import com.fatec.scireclass.service.exceptions.MatriculaNotFoundException;
import com.fatec.scireclass.service.exceptions.TokenInvalidoException;
import com.fatec.scireclass.service.exceptions.TokenNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioDesativadoException;
import com.fatec.scireclass.service.exceptions.UsuarioNotFoundException;
import com.fatec.scireclass.service.exceptions.UsuarioUnauthorizedException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MatriculaNotFoundException.class, CursoNotFoundException.class,UsuarioNotFoundException.class,TokenNotFoundException.class,ChatNotFoundException.class,CategoriaNotFoundException.class,ResourceNotFoundException.class})
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MatriculaNaoFinalizadaException.class, CursoNotVagasException.class, EmailInvalidoException.class, TokenInvalidoException.class, UsuarioDesativadoException.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsuarioUnauthorizedException.class})
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}