package com.wanger.servlets.managers;


import com.opencsv.CSVReader;
import com.wanger.dataDefines.Match;
import com.wanger.dataDefines.MatchResultForTeam;
import com.wanger.exceptions.InvalidSubmittedFileException;
import com.wanger.mongoDB.MatchDataOperation;
import com.wanger.mongoDB.TeamDataOperation;
import com.wanger.tools.CSVProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.List;

@WebServlet(urlPatterns = "/manager/upload")
@MultipartConfig
public class UploadFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part file = req.getPart("CSV_file");
        String fileName = file.getSubmittedFileName();
        try {
            if (!fileName.endsWith(".csv")) {
                throw new InvalidSubmittedFileException("文件不是CSV格式的");
            }
        } catch (InvalidSubmittedFileException e) {
            resp.sendRedirect("/manager/html/uploadFile.html?status=invalid_file");
            return;
        }
        
        CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())));
        List<Match> matches = CSVProcess.parseFile(reader);
        for (Match match : matches) {
            String matchId = MatchDataOperation.save(match);
            
            String teamAId = match.getTeamAId();
            String teamBId = match.getTeamBId();
            TeamDataOperation.addMatchResult(teamAId, new MatchResultForTeam(matchId, match.getTeamAScore()));
            TeamDataOperation.addMatchResult(teamBId, new MatchResultForTeam(matchId, match.getTeamBScore()));
        }
        resp.sendRedirect("/manager/html/uploadFile.html?status=success");
    }
    
    
}
