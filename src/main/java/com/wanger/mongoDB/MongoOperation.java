package com.wanger.mongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wanger.statics.Statics;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

abstract public class MongoOperation {
    
    protected static MongoClient GetStdMongoClient() {
        CodecRegistry pojocodecRegistry = CodecRegistries
                .fromProviders(PojoCodecProvider
                                       .builder()
                                       .automatic(true)
                                       .build());
        CodecRegistry codecRegistry = CodecRegistries
                .fromProviders(MongoClientSettings.getDefaultCodecRegistry(), pojocodecRegistry);
        
        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyConnectionString(
                        new ConnectionString(Statics.MONGO_CONNECTION_STRING))
                .codecRegistry(codecRegistry)
                .build();
        
        return MongoClients.create(settings);
    }
    
    protected static MongoCollection<Document> GetCollection(MongoClient mongoClient, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(Statics.DATABASE_NAME);
        return database.getCollection(collectionName);
    }
    
}

