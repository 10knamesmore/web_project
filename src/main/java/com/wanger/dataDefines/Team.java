package com.wanger.dataDefines;

import java.util.ArrayList;

public class Team implements AbstractSavableData {
    private String ID;
    private String teamName;
    private ArrayList<MatchResultForTeam> matchResultForTeams;
    
    public Team() {
        super();
    }
    
    public Team(String teamName) {
        this.teamName = teamName;
    }
    
    public Team(String teamName, ArrayList<MatchResultForTeam> matchResultForTeams) {
        this.teamName = teamName;
        this.matchResultForTeams = matchResultForTeams;
    }
    
    public ArrayList<MatchResultForTeam> getMatchResults() {
        return matchResultForTeams;
    }
    
    public String getID() {
        return ID;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setMatchResults(ArrayList<MatchResultForTeam> matchResultForTeams) {
        this.matchResultForTeams = matchResultForTeams;
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