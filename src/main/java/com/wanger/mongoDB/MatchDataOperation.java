package com.wanger.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.wanger.dataDefines.AbstractSavableData;
import com.wanger.exceptions.DataNotFoundException;
import com.wanger.exceptions.DocumentExistException;
import com.wanger.exceptions.SavedDataClassException;
import com.wanger.exceptions.TeamNotExitsException;
import com.wanger.statics.Statics;
import org.bson.Document;

import java.lang.reflect.Field;

public class MatchDataOperation extends MongoOperation {
    private static final MongoClient mongoClient = MongoOperation.GetStdMongoClient();
    private static final MongoCollection<Document> collection = GetCollection(mongoClient,
                                                                              Statics.MATCH_COLLECTION_NAME);
    
    /**
     * 将 Team 对象保存到由 Statics.TEAM_COLLECTION_NAME 指定的 MongoDB 集合中。
     *
     * @param <T>  数据的类型，必须继承自 AbstractSavableData。
     * @param data 要保存的 Team 对象。它必须是名为 "Team" 的类的实例。
     * @return 插入文档的 ID，作为字符串返回。
     * @throws SavedDataClassException 如果提供的数据不是名为 "Team" 的类的实例。
     * @throws RuntimeException        如果访问字段、调用方法时出错，或数据插入失败。
     */
    public static <T extends AbstractSavableData> String save(T data) throws DocumentExistException {
        if (!"com.wanger.dataDefines.Match".equals(data.getClass()
                                                       .getName())) {
            System.out.println(data.getClass()
                                   .getName());
            throw new SavedDataClassException("应该输入一个Match对象");
        }
        try {
            Document documentToInset = new Document();
            
            Class<? extends AbstractSavableData> dataClass = data.getClass();
            Field[] fields = dataClass.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(data);
                if ("matchDate".equals(key)) {
                    value = value.toString();
                }
                if (!"ID".equals(key)) {
                    documentToInset.put(key, value);
                }
            }
            Document document = new Document("$set", documentToInset);
            collection.updateOne(documentToInset, document,
                                 new UpdateOptions().upsert(true));
            
            Document updatedDoc = collection.find(documentToInset)
                                            .first();
            if (updatedDoc != null) {
                return String.valueOf(updatedDoc.getObjectId("_id"));
            }
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法获得字段访问权限");
        } catch (TeamNotExitsException e) {
            throw new RuntimeException("球队的数据库尚未建立,请联系数据库管理者先建立数据库");
        } catch (NullPointerException e) {
            throw new RuntimeException("插入数据时出现空指针异常");
        }
    }
    
    /**
     * 读取第一个数据,以JSON格式的字符串返回
     *
     * @return JSON 格式的字符串
     */
    public static String ReadFirst() {
        Document firstDocument = collection.find()
                                           .first();
        if (firstDocument != null) {
            return firstDocument.toJson();
        } else {
            throw new DataNotFoundException("找不到数据");
        }
    }
}
