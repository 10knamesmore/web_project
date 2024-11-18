package com.wanger.dataDefines;

import java.util.ArrayList;

public class Team implements AbstractSavableData {
    private String ID;
    private String teamName;
    private ArrayList<MatchResult> matchResults;
    
    public Team() {
        super();
    }
    
    public Team(String teamName) {
        this.teamName = teamName;
    }
    
    public Team(String teamName, ArrayList<MatchResult> matchResults) {
        this.teamName = teamName;
        this.matchResults = matchResults;
    }
    
    public ArrayList<MatchResult> getMatchResults() {
        return matchResults;
    }
    
    public String getID() {
        return ID;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setMatchResults(ArrayList<MatchResult> matchResults) {
        this.matchResults = matchResults;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    
    @Override
    public String toString() {
        return "Team ID: " +
                ID +
                "\n" +
                "Team Name: " +
                teamName +
                "\n";
    }
}