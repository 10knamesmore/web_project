package com.wanger.dataDefines;

public class Matches {
    public static Match.Result GenResult(int TeamAScore, int TeamBResult) {
        return new Match.Result(TeamAScore, TeamBResult);
    }
    
    /**
     * 将 "int:int" 形式的字符串解析为MatchResult
     *
     * @param str 类似1:1形式的result
     * @return MatchResul
     */
    public static Match.Result parse(String str) {
        String[] split = str.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid result format: " + str);
        }
        return new Match.Result(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
