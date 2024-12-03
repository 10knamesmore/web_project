package com.wanger.servlets.managers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/managerLogin")
public class ManagerLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        if("123".equals(username) && "123".equals(password)) {
            resp.sendRedirect("/manager/html/managerInterface.html");
        } else {
            resp.sendRedirect("/manager/html/managerLogin.html?status=invalid");
        }
    }
}
