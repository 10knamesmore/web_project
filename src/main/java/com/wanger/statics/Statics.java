package com.wanger.statics;

import com.wanger.data.*;
import com.wanger.mongoDB.MongoOperation;

import java.time.LocalDate;
import java.util.ArrayList;

public class Statics {
    public static final String DATABASE_NAME = "FootballGamesDatabases";
    public static final String MONGO_CONNECTION_STRING = "mongodb://localhost:27017";
    public static final String TEAM_COLLECTION_NAME = "teams";
    public static final String MATCH_COLLECTION_NAME = "matches";
    public static final Team TEST_TEAM_DATA_A;
    public static final Team TEST_TEAM_DATA_B;
    public static final Match TEST_MATCH_DATA;
    
    static {
        //初始化TEAM_A
        Coach testCoach1 = Coaches.getCoach("李鹏霄", "001", "002", "003", "004", "005", "006");
        Coach testCoach2 = Coaches.getCoach("李铁", "007", "008", "009", "010", "011", "012");
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(testCoach1);
        coaches.add(testCoach2);
        TEST_TEAM_DATA_A = new Team("中国", coaches);
        String teamA_ID = MongoOperation.SaveData(TEST_TEAM_DATA_A, Statics.TEAM_COLLECTION_NAME);
        
        
        //初始化TEAM_B
        Coach testCoach3 = Coaches.getCoach("赫尔韦·雷纳德", "013", "014", "015", "016", "017", "018");
        Coach testCoach4 = Coaches.getCoach("法赫德·阿尔穆瓦拉德", "019", "020", "021", "022", "023", "024");
        ArrayList<Coach> coachesB = new ArrayList<>();
        coachesB.add(testCoach3);
        coachesB.add(testCoach4);
        TEST_TEAM_DATA_B = new Team("沙特", coachesB);
        String teamB_ID = MongoOperation.SaveData(TEST_TEAM_DATA_B, Statics.TEAM_COLLECTION_NAME);
        
        //初始化比赛结果
        LocalDate matchDate = LocalDate.of(2022, 3, 20);
        Match.Result result = Matches.GenResult(0, 2);
        String matchType = "十二强赛";
        TEST_MATCH_DATA = new Match(matchDate, teamA_ID, teamA_ID, teamB_ID, result, matchType);
    }
    
}
