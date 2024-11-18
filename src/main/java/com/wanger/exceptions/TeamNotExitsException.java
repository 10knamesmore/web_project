package com.wanger.exceptions;

import com.mongodb.MongoException;
import org.bson.BsonDocument;

public class TeamNotExitsException extends MongoException {
    public TeamNotExitsException(String msg) {
        super(msg);
    }
    
    public TeamNotExitsException(int code, String msg) {
        super(code, msg);
    }
    
    public TeamNotExitsException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public TeamNotExitsException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
    
    public TeamNotExitsException(int code, String msg, BsonDocument response) {
        super(code, msg, response);
    }
}
