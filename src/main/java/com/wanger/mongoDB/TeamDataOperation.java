package com.wanger.mongoDB;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.wanger.dataDefines.AbstractSavableData;
import com.wanger.dataDefines.MatchResultForTeam;
import com.wanger.exceptions.DataNotFoundException;
import com.wanger.exceptions.SavedDataClassException;
import com.wanger.exceptions.TeamNotExitsException;
import com.wanger.statics.Statics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

public class TeamDataOperation extends MongoOperation {
    private static final MongoClient mongoClient = MongoOperation.GetStdMongoClient();
    private static final MongoCollection<Document> collection = MongoOperation.GetCollection(mongoClient,
                                                                                             Statics.TEAM_COLLECTION_NAME);
    
    private TeamDataOperation() {
    }
    
    /**
     * 将 Team 对象保存到由 Statics.TEAM_COLLECTION_NAME 指定的 MongoDB 集合中。
     *
     * @param <T>  数据的类型，必须继承自 AbstractSavableData。
     * @param data 要保存的 Team 对象。它必须是名为 "Team" 的类的实例。
     * @return 插入文档的 ID，作为字符串返回。
     * @throws SavedDataClassException 如果提供的数据不是名为 "Team" 的类的实例。
     * @throws RuntimeException        如果访问字段、调用方法时出错，或数据插入失败。
     */
    public static <T extends AbstractSavableData> String save(T data) {
        if (!"com.wanger.dataDefines.Team".equals(data.getClass()
                                                      .getName())) {
            System.out.println(data.getClass()
                                   .getName());
            throw new SavedDataClassException("应该输入一个Team对象");
        }
        try {
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
                throw new MongoException("数据插入失败");
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
    
    /**
     * 为制定的team添加比赛结果
     *
     * @param result 比赛的结果
     * @param teamId 要添加的team的名字 如 中国
     */
    public static void addMatchResult(String teamId, MatchResultForTeam result) throws TeamNotExitsException {
        Document filter = new Document("_id", new ObjectId(teamId));
        Document existingDoc = collection.find(filter)
                                         .first();
        
        if (existingDoc == null) {
            // 文档不存在，插入新文档
            throw new TeamNotExitsException("这个team不存在,请先插入一个文档");
        } else if (!existingDoc.containsKey("matchResults") || !(existingDoc.get(
                "matchResults") instanceof ArrayList)) {
            // 文档存在，但 matchResults 字段不存在或不是数组
            collection.updateOne(filter, set("matchResults", new ArrayList<>()));
        }
        
        collection.updateOne(filter, push("matchResults", result));
    }
    
    
    public static ArrayList<String> findBy(Document filter) {
        ArrayList<String> result = new ArrayList<>();
        
        FindIterable<Document> documents = collection.find(filter);
        for (Document document : documents) {
            result.add(document.getString("_id"));
        }
        
        return result;
    }
    
    /**
     * 找team
     *
     * @param teamName 要找的team的name
     * @return team的id, 如果为"None"则表示没有
     */
    public static String findTeamByName(String teamName) {
        Document filter = new Document("teamName", teamName);
        Document document = collection.find(filter)
                                      .first();
        if (document != null) {
            return document.get("_id")
                           .toString();
        } else {
            return "None";
        }
    }
    
    public static String findTeamById(String teamId) {
        String name = Objects.requireNonNull(collection.find(new Document("_id", new ObjectId(teamId)))
                                                       .first())
                             .get("teamName")
                             .toString();
        return Objects.requireNonNullElse(name, "None");
    }
    
    public static String createTeamDocument(String teamName) {
        Document document = new Document("teamName", teamName)
                .append("matchResults", new ArrayList<>());
        InsertOneResult insertOneResult = collection.insertOne(document);
        return Objects.requireNonNull(insertOneResult.getInsertedId())
                      .asObjectId()
                      .getValue()
                      .toString();
    }
    
}
