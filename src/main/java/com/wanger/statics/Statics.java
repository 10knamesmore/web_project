package com.wanger.statics;

public class Statics {
    public static final String DATABASE_NAME = "FootballGamesDatabases";
    public static final String MONGO_CONNECTION_STRING = "mongodb://localhost:27017";
    public static final String TEAM_COLLECTION_NAME = "teams";
    public static final String MATCH_COLLECTION_NAME = "matches";
    public static final String MANAGERS_ACCOUNTS_COLLECTION_NAME = "managers";
    
//    public static final Team TEST_TEAM_DATA_A;
//    public static final Team TEST_TEAM_DATA_B;
//    public static final Match TEST_MATCH_DATA;
    
//    static {
//        TEST_TEAM_DATA_A = new Team("中国",new ArrayList<>());
//        String teamA_ID = TeamDataOperation.save(TEST_TEAM_DATA_A);
//
//        //初始化TEAM_B
//        TEST_TEAM_DATA_B = new Team("沙特",new ArrayList<>());
//        String teamB_ID = TeamDataOperation.save(TEST_TEAM_DATA_B);
//
//        //初始化比赛结果
//        LocalDate matchDate = LocalDate.of(2022, 3, 20);
//        Match.Result result = Matches.GenResult(0, 2);
//        String matchType = "十二强赛";
//        TEST_MATCH_DATA = new Match(matchDate, teamA_ID, teamA_ID, teamB_ID, result, matchType);
//    }
    
}
