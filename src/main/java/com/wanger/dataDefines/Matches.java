package com.wanger.dataDefines;

public class Matches {
    public static Match.Result GenResult(int TeamAScore,int TeamBResult) {
        return new Match.Result(TeamAScore, TeamBResult);
    }
}
