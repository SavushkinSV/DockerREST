package com.example.exception;

public class DBConnectException extends RuntimeException{
    public DBConnectException(String message) {
        super(message);
    }
}
