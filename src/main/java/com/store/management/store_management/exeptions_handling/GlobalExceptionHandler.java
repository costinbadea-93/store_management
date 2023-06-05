package com.store.management.store_management.exeptions_handling;

import com.store.management.store_management.exceptions.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException dataNotFoundException){
        return ResponseEntity.badRequest()
                .body(dataNotFoundException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleHibernateValidation(MethodArgumentNotValidException methodArgumentNotValidException){

        var errorMessage = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(",\n"));

        return ResponseEntity.badRequest()
                .body(errorMessage);
    }
}
