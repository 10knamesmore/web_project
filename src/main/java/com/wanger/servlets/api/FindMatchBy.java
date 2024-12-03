package com.wanger.servlets.api;

import com.wanger.mongoDB.MatchDataOperation;
import com.wanger.mongoDB.TeamDataOperation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;

@WebServlet("/api/findmatchby")
public class FindMatchBy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dataType = req.getParameter("datatype");
        String dataValue = req.getParameter("datavalue");
        Document filter = new Document();
        switch (dataType) {
            case "matchDate":
                filter.put("matchDate", dataValue);
                break;
            case "teamName":
                String id = TeamDataOperation.findTeamByName(dataValue);
                if ("None".equals(id)) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    return;
                }
                filter.put("homeTeamId", id);
                break;
            case "matchType":
                filter.put("matchType", dataValue);
                break;
        }
        String resultJson = MatchDataOperation.findBy(filter);
        resp.setContentType("application/json");
        resp.getWriter()
            .write(resultJson);
        resp.flushBuffer();
    }
}
