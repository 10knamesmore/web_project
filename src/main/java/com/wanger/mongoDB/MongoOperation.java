package com.wanger.mongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.wanger.data.AbstractSavableData;
import com.wanger.statics.Statics;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MongoOperation {
    
    public static <T extends AbstractSavableData> String SaveData(T data, String collectionName) {
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient, collectionName);
            
            return SaveTo(data, collection);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("当获取字段: " + data.getClass()
                                                            .getName() + " 时发生错误", e);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("反射时发生错误", e);
        }
    }
    
    private static MongoClient GetStdMongoClient() {
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
    
    private static MongoCollection<Document> GetCollection(MongoClient mongoClient, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(Statics.DATABASE_NAME);
        return database.getCollection(collectionName);
    }
    
    
    private static String SaveTo(AbstractSavableData data,
                                 MongoCollection<Document> collection) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Document doc = new Document();
        Class<? extends AbstractSavableData> dataClass = data.getClass();
        Field[] fields = dataClass
                .getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!"ID".equals(field.getName())) {
                doc.append(field.getName(), field.get(data));
            }
        }
        InsertOneResult insertOneResult = collection.insertOne(doc);
        if (insertOneResult.getInsertedId() == null) {
            throw new MongoException("无法写入数据库,请检查格式");
        } else {
            String id = insertOneResult.getInsertedId()
                                       .asObjectId()
                                       .getValue()
                                       .toString();
            Method setID = dataClass.getMethod("setID", String.class);
            setID.invoke(data, id);
            return id;
        }
    }
    
    public static String ReadTeam() {
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient, Statics.TEAM_COLLECTION_NAME);
            Document document = collection.find()
                                          .first();
            
            return document.toJson();
        }
    }
    public static String ReadMatch() {
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient, Statics.MATCH_COLLECTION_NAME);
            Document document = collection.find()
                                          .first();
            
            return document.toJson();
        }
    }
}
