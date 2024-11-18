package com.wanger.exceptions;

import com.mongodb.MongoException;
import org.bson.BsonDocument;

public class DocumentExistException extends MongoException {
    public DocumentExistException(String msg) {
        super(msg);
    }
    
    public DocumentExistException(int code, String msg) {
        super(code, msg);
    }
    
    public DocumentExistException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public DocumentExistException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
    
    public DocumentExistException(int code, String msg, BsonDocument response) {
        super(code, msg, response);
    }
}
