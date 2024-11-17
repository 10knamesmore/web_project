package com.wanger.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException() {
        super();
    }
    
    public DataNotFoundException(String message) {
        super(message);
    }
}
