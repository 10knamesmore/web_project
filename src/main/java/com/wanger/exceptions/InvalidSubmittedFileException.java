package com.wanger.exceptions;

import com.mongodb.MongoException;
import org.bson.BsonDocument;

public class InvalidSubmittedFileException extends MongoException {
    public InvalidSubmittedFileException(String msg) {
        super(msg);
    }
    
    public InvalidSubmittedFileException(int code, String msg) {
        super(code, msg);
    }
    
    public InvalidSubmittedFileException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public InvalidSubmittedFileException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
    
    public InvalidSubmittedFileException(int code, String msg, BsonDocument response) {
        super(code, msg, response);
    }
}
