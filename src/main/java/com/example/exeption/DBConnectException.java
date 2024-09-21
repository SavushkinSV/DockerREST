package com.example.exeption;

public class DBConnectException extends RuntimeException{
    public DBConnectException(String message) {
        super(message);
    }
}
