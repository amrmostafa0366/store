package org.example.error;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends ApiBaseException{
    public IllegalArgumentException(String message) {
        super(message);
    }

    @Override
    HttpStatus getStatusCode() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
