package com.wanger.data;

import java.util.ArrayList;

public class Team {
    private String teamid;
    private String teamName;
    private ArrayList<Coach> coaches;
    
    public Team() {
        super();
    }
    
    /*
     * 用于从数据库中读取已有的数据
     *
     * */
    public Team(String teamid, String teamName, ArrayList<Coach> coaches) {
        if (!teamid.isEmpty())
            this.teamid = teamid;
        else {
            throw new IllegalArgumentException("Team ID cannot be empty.");
        }
        if (!"null".equals(teamName))
            this.teamName = teamName;
        else {
            this.teamName = "无数据";
        }
        if (coaches != null && !coaches.isEmpty())
            this.coaches = coaches;
        else {
            this.coaches = new ArrayList<>();
        }
    }
    /*
     * 用于将数据存储到数据库中
     *
     */
    public Team(String teamName, ArrayList<Coach> coaches) {
        if(!teamName.isEmpty()) {
            this.teamName = teamName;
        } else {
            this.teamName = "null";
        }
        if(coaches == null || coaches.isEmpty()) {
            this.coaches = new ArrayList<>();
        } else {
            this.coaches = coaches;
        }
    }
    
    public String getTeamid() {
        return teamid;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public ArrayList<Coach> getCoaches() {
        return coaches;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }
    
    public void setCoaches(ArrayList<Coach> coaches) {
        this.coaches = coaches;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Team ID: ").append(teamid).append("\n");
        stringBuilder.append("Team Name: ").append(teamName).append("\n");
        stringBuilder.append("Coaches: ").append(coaches.toString()).append("\n");
        return stringBuilder.toString();
    }
}