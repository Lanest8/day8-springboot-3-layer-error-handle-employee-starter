package com.example.demo.exception;

public class InvalidActiveEmployeeException extends RuntimeException {
    public InvalidActiveEmployeeException(String message) {
        super(message);
    }
}
