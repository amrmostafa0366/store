package org.example.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException {

    public ConflictException(String message) {
        super(message);
    }

    @Override
    HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
