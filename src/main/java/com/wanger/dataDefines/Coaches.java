package com.wanger.dataDefines;

import java.util.ArrayList;
import java.util.Arrays;

public class Coaches {
    public static Coach getCoach(String name, String... matchesid) {
        ArrayList<String> matchesids = new ArrayList<>(Arrays.asList(matchesid));
        return new Coach(name, matchesids);
    }
}
