package com.wanger.mongoDB;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.wanger.dataDefines.AbstractSavableData;
import com.wanger.exceptions.DataNotFoundException;
import com.wanger.exceptions.DocumentExistException;
import com.wanger.exceptions.SavedDataClassException;
import com.wanger.exceptions.TeamNotExitsException;
import com.wanger.statics.Statics;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchDataOperation extends MongoOperation {
    private static final MongoClient mongoClient = MongoOperation.GetStdMongoClient();
    private static final MongoCollection<Document> collection = GetCollection(mongoClient,
                                                                              Statics.MATCH_COLLECTION_NAME);
    
    private MatchDataOperation() {
    
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
            collection.updateOne(documentToInset, document, new UpdateOptions().upsert(true));
            
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
    
    /**
     * 读取所有比赛的数据,以JSON格式的字符串返回
     *
     * @return JSON 格式的字符串
     */
    public static String ReadAll() {
        List<Document> result = new ArrayList<>();
        try {
            collection.find()
                      .forEach(document -> {
                          String id = document.get("_id")
                                              .toString();
                          String matchType = document.getString("matchType");
                          String matchDate = document.getString("matchDate");
                          String homeTeamId = document.getString("homeTeamId");
                          String teamAId = document.getString("teamAId");
                          String teamBId = document.getString("teamBId");
                          Document resultDocument = document.get("result", Document.class);
                          Integer teamAScore = resultDocument.getInteger("teamAScores");
                          Integer teamBScore = resultDocument.getInteger("teamBScores");
                          
                          String homeTeamName = TeamDataOperation.findTeamById(homeTeamId);
                          String awayTeamName = homeTeamId.equals(teamAId) ? TeamDataOperation.findTeamById(
                                  teamBId) : TeamDataOperation.findTeamById(teamAId);
                          
                          Integer homeTeamScore = homeTeamId.equals(teamAId) ? teamAScore : teamBScore;
                          Integer awayTeamScore = homeTeamId.equals(teamAId) ? teamBScore : teamAScore;
                          
                          Document doc = new Document().append("id", id)
                                                       .append("matchType", matchType)
                                                       .append("matchType", matchType)
                                                       .append("matchDate", matchDate)
                                                       .append("homeTeamName", homeTeamName)
                                                       .append("awayTeamName", awayTeamName)
                                                       .append("homeTeamScore", homeTeamScore)
                                                       .append("awayTeamScore", awayTeamScore);
                          
                          result.add(doc);
                      });
        } catch (Exception e) {
            throw new RuntimeException("读取数据时发生错误: " + e.getMessage());
        }
        result.sort((doc1, doc2) -> doc2.getString("matchDate")
                                        .compareTo(doc1.getString("matchDate")));
        
        return result.stream()
                     .map(Document::toJson)
                     .collect(Collectors.joining(",", "[", "]"));
    }
    
    /**
     * 根据 ID 查找比赛数据,以JSON格式的字符串返回
     *
     * @param id 要查找的比赛数据的 ID
     * @return JSON 格式的字符串
     */
    public static String ReadById(String id) {
        List<Document> result = new ArrayList<>();
        collection.find(Filters.eq("teamAId", id))
                  .forEach(document -> {
                      String matchType = document.getString("matchType");
                      String matchDate = document.getString("matchDate");
                      String EnemyTeanName = TeamDataOperation.findTeamById(document.getString("teamBId"));
                      Integer teamAScore = document.get("result", Document.class)
                                                   .getInteger("teamAScores");
                      
                      result.add(new Document().append("matchType", matchType)
                                               .append("matchDate", matchDate)
                                               .append("enemyTeamName", EnemyTeanName)
                                               .append("Scores", teamAScore));
                  });
        collection.find(Filters.eq("teamBId", id))
                  .forEach(document -> {
                      String matchType = document.getString("matchType");
                      String matchDate = document.getString("matchDate");
                      String EnemyTeamName = TeamDataOperation.findTeamById(document.getString("teamAId"));
                      Integer Score = document.get("result", Document.class)
                                              .getInteger("teamBScores");
                      
                      result.add(new Document().append("matchType", matchType)
                                               .append("matchDate", matchDate)
                                               .append("enemyTeamName", EnemyTeamName)
                                               .append("Scores", Score));
                  });
        
        result.sort((doc1, doc2) -> doc2.getString("matchDate")
                                        .compareTo(doc1.getString("matchDate")));
        if (result.isEmpty()) {
            throw new DataNotFoundException("找不到数据");
        } else {
            return new Gson().toJson(result.toArray());
        }
    }
    
    public static void deleteById(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
    
    /**
     * 根据条件查找比赛数据,返回JSON,如果为空则返回[]
     *
     * @param filter 条件
     * @return JSON, 格式为[{},{},{}]
     */
    public static String findBy(Document filter) {
        ArrayList<Document> result = new ArrayList<>();
        
        collection.find(filter)
                  .forEach(document -> {
                      String id = document.get("_id")
                                          .toString();
                      String matchType = document.getString("matchType");
                      String matchDate = document.getString("matchDate");
                      String homeTeamId = document.getString("homeTeamId");
                      String teamAId = document.getString("teamAId");
                      String teamBId = document.getString("teamBId");
                      Document resultDocument = document.get("result", Document.class);
                      Integer teamAScore = resultDocument.getInteger("teamAScores");
                      Integer teamBScore = resultDocument.getInteger("teamBScores");
                      
                      String homeTeamName = TeamDataOperation.findTeamById(homeTeamId);
                      String awayTeamName = homeTeamId.equals(teamAId) ? TeamDataOperation.findTeamById(
                              teamBId) : TeamDataOperation.findTeamById(teamAId);
                      
                      Integer homeTeamScore = homeTeamId.equals(teamAId) ? teamAScore : teamBScore;
                      Integer awayTeamScore = homeTeamId.equals(teamAId) ? teamBScore : teamAScore;
                      
                      Document doc = new Document().append("id", id)
                                                   .append("matchType", matchType)
                                                   .append("matchType", matchType)
                                                   .append("matchDate", matchDate)
                                                   .append("homeTeamName", homeTeamName)
                                                   .append("awayTeamName", awayTeamName)
                                                   .append("homeTeamScore", homeTeamScore)
                                                   .append("awayTeamScore", awayTeamScore);
                      
                      result.add(doc);
                  });
        result.sort((doc1, doc2) -> doc2.getString("matchDate")
                                        .compareTo(doc1.getString("matchDate")));
        if (result.isEmpty()) {
            return "[]";
        }
        
        return result.stream()
                     .map(Document::toJson)
                     .collect(Collectors.joining(",", "[", "]"));
    }
    
    
}

