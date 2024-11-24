package com.wanger.tools;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.wanger.dataDefines.Match;
import com.wanger.dataDefines.Matches;
import com.wanger.mongoDB.TeamDataOperation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class CSVProcess {
    /**
     * 解析一个CSV文件并提取 match, 返回一个 ArrayList<Match>,函数结束时能保证所有 Match 包含的Team都建立了 Document
     *
     * @param reader 一个 CSVReader 用来读取目标CSV
     * @return a list of Match objects containing the parsed match data.
     * @throws RuntimeException if there is an error reading the CSV file or parsing its contents.
     */
    public static List<Match> parseFile(CSVReader reader) {
        List<Match> result = new ArrayList<>();
        try {
            String[] line;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d")
                                                                   .withResolverStyle(
                                                                           ResolverStyle.LENIENT);
            // 跳过顶栏
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String matchType = line[0];
                LocalDate matchDate = LocalDate.parse(line[1], dateTimeFormatter);
                String homeTeamName = line[2];
                Match.Result matchResult = Matches.parse(line[3]);
                String guestTeamName = line[4];
                
                String homeTeamId = TeamDataOperation.findTeam(homeTeamName);
                if ("None".equals(homeTeamId)) {
                    homeTeamId = TeamDataOperation.createTeamDocument(homeTeamName);
                }
                String guestTeamId = TeamDataOperation.findTeam(guestTeamName);
                if ("None".equals(guestTeamId)) {
                    guestTeamId = TeamDataOperation.createTeamDocument(guestTeamName);
                }
                
                Match match = new Match(matchDate, homeTeamId, homeTeamId, guestTeamId, matchResult, matchType);
                result.add(match);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        
        return result;
    }
}
