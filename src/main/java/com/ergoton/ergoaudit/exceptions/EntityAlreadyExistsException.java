package com.ergoton.ergoaudit.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException() {
    }

}
