package com.wanger.dataDefines;

public class MatchResultForTeam {
    private String matchId;
    private int score;
    
    public MatchResultForTeam() {
    }
    
    public MatchResultForTeam(String matchId, int score) {
        this.matchId = matchId;
        this.score = score;
    }
    
    public String getMatchId() {
        return matchId;
    }
    
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
}
