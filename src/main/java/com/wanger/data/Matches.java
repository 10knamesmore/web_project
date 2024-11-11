package com.wanger.data;

import java.util.regex.MatchResult;

public class Matches {
    public static Match.Result GenResult(int TeamAScore,int TeamBResult) {
        return new Match.Result(TeamAScore, TeamBResult);
    }
}
