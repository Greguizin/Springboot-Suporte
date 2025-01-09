package com.example.demo.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class Exceptions {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        // Criar o objeto de resposta com status e mensagem
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatusCode().value(), 
            ex.getReason() 
        );

        // Retornar o erro formatado em JSON
        return new ResponseEntity<>(errorResponse, ex.getStatusCode()); // Usar getStatusCode() ao invés de getStatus()
    }
}
