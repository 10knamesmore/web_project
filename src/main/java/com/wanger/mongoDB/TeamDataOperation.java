package com.wanger.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.wanger.dataDefines.AbstractSavableData;
import com.wanger.exceptions.DataNotFoundException;
import com.wanger.exceptions.SavedDataClassException;
import com.wanger.statics.Statics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import org.bson.Document;

import java.lang.reflect.Field;

public class TeamDataOperation extends MongoOperation {
    /**
     * 将 Team 对象保存到由 Statics.TEAM_COLLECTION_NAME 指定的 MongoDB 集合中。
     *
     * @param <T>  数据的类型，必须继承自 AbstractSavableData。
     * @param data 要保存的 Team 对象。它必须是名为 "Team" 的类的实例。
     * @return 插入文档的 ID，作为字符串返回。
     * @throws SavedDataClassException 如果提供的数据不是名为 "Team" 的类的实例。
     * @throws RuntimeException        如果访问字段、调用方法时出错，或数据插入失败。
     */
    public static  <T extends AbstractSavableData> String Save(T data) {
        if (!"Team".equals(data.getClass()
                               .getName())) {
            throw new SavedDataClassException("应该输入一个Team对象");
        }
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient,
                                                                 Statics.TEAM_COLLECTION_NAME);
            Document document = new Document();
            Class<? extends AbstractSavableData> dataClass = data.getClass();
            Field[] fields = dataClass.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                if (!"ID".equals(field.getName())) {
                    document.put(field.getName(), field.get(data));
                }
            }
            InsertOneResult insertOneResult = collection.insertOne(document);
            if (insertOneResult.getInsertedId() == null) {
                throw new RuntimeException("数据插入失败");
            } else {
                //设置ID
                String id = insertOneResult.getInsertedId()
                                           .asObjectId()
                                           .getValue()
                                           .toString();
                Method setID = dataClass.getDeclaredMethod("setID", String.class);
                setID.invoke(data, id);
                return id;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法获得字段访问权限");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("找不到设置ID的方法");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("无法调用方法");
        }
    }
    
    /**
     * 读取第一个数据,以JSON格式的字符串返回
     * @return JSON 格式的字符串
     */
    public static String ReadFirst() {
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient,
                                                                 Statics.TEAM_COLLECTION_NAME);
            Document firstDocument = collection.find()
                                               .first();
            if (firstDocument != null) {
                return firstDocument.toJson();
            } else {
                throw new DataNotFoundException("找不到数据");
            }
        }
    }
}
