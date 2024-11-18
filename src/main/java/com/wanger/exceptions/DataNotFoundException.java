package com.wanger.exceptions;

import com.mongodb.MongoException;
import org.bson.BsonDocument;

public class DataNotFoundException extends MongoException {
    
    public DataNotFoundException(String msg) {
        super(msg);
    }
    
    public DataNotFoundException(int code, String msg) {
        super(code, msg);
    }
    
    public DataNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public DataNotFoundException(int code, String msg, BsonDocument response) {
        super(code, msg, response);
    }
    
    public DataNotFoundException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
}
