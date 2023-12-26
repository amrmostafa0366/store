package org.example.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
