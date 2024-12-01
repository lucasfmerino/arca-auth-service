package com.arca.auth_service.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler
{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> invalidArgumentsHandler(MethodArgumentNotValidException exception)
    {
        Map<String, String> exceptionMap = new HashMap<>();

        List<FieldError> fields = exception.getBindingResult().getFieldErrors();

        for (FieldError field : fields)
        {
            exceptionMap.put(field.getField(), field.getDefaultMessage());
        }
        return exceptionMap;
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> dataIntegrityViolationException()
    {
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("erro", "Objeto já cadastrado");
        return exceptionMap;
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handeBusinessRules(ValidationException e)
    {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound404()
    {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e)
    {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}