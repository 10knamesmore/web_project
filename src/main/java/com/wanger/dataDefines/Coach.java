package com.wanger.dataDefines;

import java.util.ArrayList;

public class Coach{
    private String name;
    private ArrayList<String> coachedMatchIds;
    
    public Coach() {
        super();
    }
    
    Coach(String name, ArrayList<String> matchesid) {
        this.name = name;
        this.coachedMatchIds = new ArrayList<>();
        if (matchesid != null) {
            this.coachedMatchIds = matchesid;
        }
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<String> getCoachedMatchIds() {
        return coachedMatchIds;
    }
    
    public void setCoachedMatchIds(ArrayList<String> coachedMatchIds) {
        this.coachedMatchIds = coachedMatchIds;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Coached Match IDs: ").append(coachedMatchIds).append("\n");
        return stringBuilder.toString();
    }
}
