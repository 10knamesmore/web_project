package com.wanger.exceptions;

public class SavedDataClassException extends IllegalArgumentException {
    public SavedDataClassException() {
        super();
    }
    
    public SavedDataClassException(String message) {
        super(message);
    }
}
