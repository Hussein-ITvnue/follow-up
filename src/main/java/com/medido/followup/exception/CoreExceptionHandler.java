package com.medido.followup.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.w3c.dom.events.EventException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CoreExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String details = ex.getMessage();
        ErrorResponseDetail error = new
                ErrorResponseDetail(ResponseCodes.NOT_FOUND.getType(), ResponseCodes.NOT_FOUND.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), details);
        ErrorResponseEntity response = new ErrorResponseEntity();
        response.append(error);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceExistsException.class})
    protected ResponseEntity<Object> handleUserExists(ResourceExistsException ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String details = ex.getMessage();
        ErrorResponseDetail error = new
                ErrorResponseDetail(ResponseCodes.CONFLICT.getType(), ResponseCodes.CONFLICT.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), details);

        ErrorResponseEntity response = new ErrorResponseEntity();
        response.append(error);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class, BadRequestException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String message = ex.getMessage();
        ErrorResponseDetail error = new
                ErrorResponseDetail(ResponseCodes.BAD_REQUEST.getType(), ResponseCodes.BAD_REQUEST.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), message);
        ErrorResponseEntity response = new ErrorResponseEntity();
        response.append(error);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        List<ErrorResponseDetail> details = ex.getAllErrors().stream().map(error ->
                new ErrorResponseDetail(ResponseCodes.BAD_REQUEST.getType(), ResponseCodes.BAD_REQUEST.getCode(),
                        ((ServletWebRequest) request).getRequest().getRequestURI(), error.getDefaultMessage())
        ).collect(Collectors.toList());
        ErrorResponseEntity response = new ErrorResponseEntity();
        response.setErrors(details);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EventException.class)
    protected ResponseEntity<ErrorResponseEntity> handleGeneralException(Exception ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String message = ex.getMessage();
        ErrorResponseDetail error = new
                ErrorResponseDetail(ResponseCodes.BAD_REQUEST.getType(), ResponseCodes.BAD_REQUEST.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), message);
        ErrorResponseEntity response = new ErrorResponseEntity();
        response.append(error);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponseEntity> handleAnyException(Exception ex, WebRequest request) throws IOException {
        ex.printStackTrace();
        String message = ex.getMessage();
        ErrorResponseDetail error = new
                ErrorResponseDetail(ResponseCodes.INTERNAL_SERVER_ERROR.getType(),
                ResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), message);
        ErrorResponseEntity response = new ErrorResponseEntity();
        response.append(error);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Continue with empty response when a clientAbortException happens
    @ExceptionHandler(value = ClientAbortException.class)
    protected ResponseEntity<ErrorResponseEntity> handleClientAbortException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponseEntity(), HttpStatus.OK);
    }

}
