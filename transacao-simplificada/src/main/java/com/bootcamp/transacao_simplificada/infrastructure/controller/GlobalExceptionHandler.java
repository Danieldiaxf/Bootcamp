package com.bootcamp.transacao_simplificada.infrastructure.controller;

import com.bootcamp.transacao_simplificada.infrastructure.exceptions.BadRequestException;
import com.bootcamp.transacao_simplificada.infrastructure.exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( IllegalAccessException.class )
    public ResponseEntity<String> handleIllegalAccessException( IllegalAccessException e ) {
        return new ResponseEntity<>( "erro" + e.getMessage(), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( BadRequestException.class )
    public ResponseEntity<String> handleBadRequestException( BadRequestException e ) {
        return new ResponseEntity<>( "erro" + e.getMessage(), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( UserNotFound.class )
    public ResponseEntity<String> handeUserNotFoundException( UserNotFound e ) {
        return new ResponseEntity<>( "erro" + e.getMessage(), HttpStatus.NOT_FOUND );
    }

}
