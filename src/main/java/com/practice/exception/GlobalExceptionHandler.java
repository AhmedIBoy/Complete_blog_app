package com.practice.exception;

import com.practice.util.ErrorDetaile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> HandelResourceNotFoundException(ResourceNotFound e, WebRequest webRequest){
        ErrorDetaile error = new ErrorDetaile(HttpStatus.NOT_FOUND,e.getMessage(), LocalDateTime.now(),webRequest.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
