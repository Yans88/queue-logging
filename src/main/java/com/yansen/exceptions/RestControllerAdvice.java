package com.yansen.exceptions;


import com.yansen.dtos.responses.ResponseSaveData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@EnableWebMvc
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationUsernameEmailException.class)
    public ResponseEntity<?> validationErrorExceptionHandler(Exception ex, WebRequest request) {
        ResponseSaveData<?> responseData = new ResponseSaveData<>();
        responseData.getMessages().add(ex.getMessage());
        log.warn(ex.getMessage());
        return handleExceptionInternal(ex, responseData, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
