package com.wanger.data;

import java.util.Date;

public class Match {
    private String matchid;
    private Date matchDate;
    private String hometeamid;
    private String teamA;
    private String teamB;
    private Result result;
    private String matchType;
    
    public Match() {
        super();
    }
    
    private class Result {
        private Integer teamAScores;
        private Integer teamBScores;
    }
}
