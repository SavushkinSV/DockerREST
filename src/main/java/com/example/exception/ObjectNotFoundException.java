package com.example.exception;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
