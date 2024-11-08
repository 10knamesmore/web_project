package com.wanger.data;

import java.util.ArrayList;

public class Coach {
    private String name;
    private ArrayList<String> coachedMatchids;
    
    public Coach() {
        super();
    }
    
    Coach(String name, ArrayList<String> matchesid) {
        this.name = name;
        this.coachedMatchids = new ArrayList<>();
        if (matchesid != null) {
            this.coachedMatchids = matchesid;
        }
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<String> getCoachedMatchids() {
        return coachedMatchids;
    }
    
    public void setCoachedMatchids(ArrayList<String> coachedMatchids) {
        this.coachedMatchids = coachedMatchids;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Coached Match IDs: ").append(coachedMatchids).append("\n");
        return stringBuilder.toString();
    }
}
