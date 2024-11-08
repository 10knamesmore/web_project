package com.wanger.mongoDB;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.wanger.data.Coach;
import com.wanger.data.Coaches;
import com.wanger.data.Team;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SaveTeamData {
    private static final Team testTeamData;
    
    static {
        Coach testCoach1 = Coaches.getCoach("李鹏霄", "001", "002", "003", "004", "005", "006");
        Coach testCoach2 = Coaches.getCoach("李铁", "007", "008", "009", "010", "011", "012");
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(testCoach1);
        coaches.add(testCoach2);
        testTeamData = new Team("中国", coaches);
    }
    
    public static void main(String[] args) {
        CodecRegistry projoCodecRegistry = CodecRegistries.fromProviders(
                PojoCodecProvider.builder().automatic(true).build()
        );
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                projoCodecRegistry
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                                               .codecRegistry(codecRegistry)
                                               .build();
        
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase FBGamesDatabase = mongoClient.getDatabase("FootballGamesDatabaes");
            MongoCollection<Document> teams = FBGamesDatabase.getCollection("teams");
            Document document = new Document();
            Field[] fields = testTeamData.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                document.append(field.getName(), field.get(testTeamData));
            }
            InsertOneResult insertOneResult = teams.insertOne(document);
            System.out.println(insertOneResult);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
