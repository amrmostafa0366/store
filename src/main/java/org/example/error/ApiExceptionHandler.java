package org.example.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<ErrorDetails> apiExceptionHandler(ApiBaseException ex, WebRequest request) {
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details,ex.getStatusCode());
    }
}
