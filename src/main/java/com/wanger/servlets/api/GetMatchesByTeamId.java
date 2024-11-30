package com.wanger.servlets.api;

import com.wanger.mongoDB.MatchDataOperation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/getmatchesbyteamid")
public class GetMatchesByTeamId extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teamid = req.getParameter("teamId");
        String json = MatchDataOperation.ReadById(teamid);
        resp.setContentType("application/json");
        resp.getWriter()
            .write(json);
        resp.flushBuffer();
    }
}
