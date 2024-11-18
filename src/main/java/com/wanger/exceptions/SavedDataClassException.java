package com.wanger.exceptions;

import com.mongodb.MongoException;
import org.bson.BsonDocument;

public class SavedDataClassException extends MongoException {
    
    public SavedDataClassException(String msg) {
        super(msg);
    }
    
    public SavedDataClassException(int code, String msg) {
        super(code, msg);
    }
    
    public SavedDataClassException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public SavedDataClassException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
    
    public SavedDataClassException(int code, String msg, BsonDocument response) {
        super(code, msg, response);
    }
}
