package com.wanger.servlets.managers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/findMatchesByInfo")
public class FindMatchByInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String info_type = req.getParameter("info_type");
        String info_value = req.getParameter("info_value");
        
        
//        switch (info_type) {
//            case "matchDate":
//        }
    }
}
