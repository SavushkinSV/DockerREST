package com.example.exeption;

public class RepositoryException extends RuntimeException  {
    public RepositoryException(String message) {
        super(message);
    }
}