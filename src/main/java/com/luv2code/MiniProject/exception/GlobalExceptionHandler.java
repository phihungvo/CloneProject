package com.luv2code.MiniProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> resourceNotFoundExceptionHandler(ResourceNotFoundException exception,
                                                                        WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetail> blogApiExceptionHandler(BlogApiException exception, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDetail> globalExceptionHandler(Exception exception, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> accessDeniedExceptionHandler(AccessDeniedException exception, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }


}
