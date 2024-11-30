package com.wanger.servlets.api;

import com.google.gson.Gson;
import com.wanger.mongoDB.TeamDataOperation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/getallteams")
public class getallteams extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Object[] array = TeamDataOperation.getAllTeamsData()
                                          .toArray();
        String json = new Gson().toJson(array);
        resp.getWriter()
            .write(json);
        resp.flushBuffer();
    }
}
