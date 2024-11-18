package com.wanger.mongoDB;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.wanger.dataDefines.AbstractSavableData;
import com.wanger.dataDefines.Match;
import com.wanger.dataDefines.MatchResult;
import com.wanger.dataDefines.Team;
import com.wanger.exceptions.DataNotFoundException;
import com.wanger.exceptions.DocumentExistException;
import com.wanger.exceptions.SavedDataClassException;
import com.wanger.exceptions.TeamNotExitsException;
import com.wanger.statics.Statics;
import org.bson.Document;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MatchDataOperation extends MongoOperation {
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
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient, Statics.MATCH_COLLECTION_NAME);
            Document documentToInset = new Document();
            
            Class<? extends AbstractSavableData> dataClass = data.getClass();
            Field[] fields = dataClass.getDeclaredFields();
            
            int score = 0;
            
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(data);
                if ("matchDate".equals(key)) {
                    value = value.toString();
                }
                if ("result".equals(key)) {
                    score = ((Match.Result) value).getTeamAScores();
                }
                if (!"ID".equals(key)) {
                    documentToInset.put(key, value);
                }
            }
            Document document = new Document("$set", documentToInset);
            UpdateResult updateResult = collection.updateOne(documentToInset, document,
                                                             new UpdateOptions().upsert(true));
            if (updateResult.getMatchedCount() > 0) {
                throw new DocumentExistException("已经存在这个document");
            } else {
                String id = String.valueOf((Objects.requireNonNull(updateResult.getUpsertedId()))
                                                   .asObjectId()
                                                   .getValue());
                Method setID = dataClass.getMethod("setID", String.class);
                setID.invoke(data, id);
                TeamDataOperation.addMatchResult(new MatchResult(id, score), ((Match) data)
                        .getHomeTeamId());
                return id;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法获得字段访问权限");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("找不到设置ID的方法");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("无法调用方法");
        } catch (TeamNotExitsException e) {
            throw new RuntimeException("球队的数据库尚未建立,请联系数据库管理者先建立数据库");
        }
        
    }
    
    /**
     * 读取第一个数据,以JSON格式的字符串返回
     *
     * @return JSON 格式的字符串
     */
    public static String ReadFirst() {
        try (MongoClient mongoClient = GetStdMongoClient()) {
            MongoCollection<Document> collection = GetCollection(mongoClient, Statics.MATCH_COLLECTION_NAME);
            Document firstDocument = collection.find()
                                               .first();
            if (firstDocument != null) {
                return firstDocument.toJson();
            } else {
                throw new DataNotFoundException("找不到数据");
            }
        }
    }
    
    /**
     * 根据date字符串查找比赛数据,返回比赛的id的String数组
     *
     * @param date 1970-0-0 格式
     * @return 比赛数据在mongoDB中的id String数组
     */
    //    public static String[] FindMatchByDate(String date) {
    //        try (MongoClient mongoClient = MongoOperation.GetStdMongoClient()) {
    //            MongoCollection<Document> collection = MongoOperation.GetCollection(mongoClient,
    //                                                                                Statics
    //                                                                                .MATCH_COLLECTION_NAME);
    //        }
    //    }
}
