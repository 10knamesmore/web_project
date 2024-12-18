package com.wanger.dataDefines;

import java.time.LocalDate;

public class Match implements AbstractSavableData {
    private String ID;
    private LocalDate matchDate;
    private String homeTeamId;
    private String teamAId;
    private String teamBId;
    private Result result;
    private String matchType;
    
    public Match() {
        super();
    }
    
    public Match(LocalDate matchDate, String homeTeamId, String teamAId, String teamBId, Result result,
                 String matchType) {
        this.matchDate = matchDate;
        this.homeTeamId = homeTeamId;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.result = result;
        this.matchType = matchType;
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    public LocalDate getMatchDate() {
        return matchDate;
    }
    
    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
    
    public String getHomeTeamId() {
        return homeTeamId;
    }
    
    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }
    
    public String getTeamAId() {
        return teamAId;
    }
    
    public void setTeamAId(String teamAId) {
        this.teamAId = teamAId;
    }
    
    public String getTeamBId() {
        return teamBId;
    }
    
    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
    
    public String getMatchType() {
        return matchType;
    }
    
    public Integer getTeamAScore() {
        return result.getTeamAScores();
    }
    
    public Integer getTeamBScore() {
        return result.getTeamBScores();
    }
    
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
    
    public static class Result {
        private Integer teamAScores;
        private Integer teamBScores;
        
        
        public Integer getTeamAScores() {
            return teamAScores;
        }
        
        public void setTeamAScores(Integer teamAScores) {
            this.teamAScores = teamAScores;
        }
        
        public Integer getTeamBScores() {
            return teamBScores;
        }
        
        public void setTeamBScores(Integer teamBScores) {
            this.teamBScores = teamBScores;
        }
        
        public Result() {
        }
        
        public Result(Integer teamAScores, Integer teamBScores) {
            this.teamAScores = teamAScores;
            this.teamBScores = teamBScores;
        }
    }
}
