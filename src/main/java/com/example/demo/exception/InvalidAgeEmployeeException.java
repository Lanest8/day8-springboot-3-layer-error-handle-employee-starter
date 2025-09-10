package com.example.demo.exception;

public class InvalidAgeEmployeeException extends RuntimeException {
    public InvalidAgeEmployeeException(String message) {
        super(message);
    }
}
