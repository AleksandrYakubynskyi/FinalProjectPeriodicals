package com.project.controller;

import com.project.command.Command;
import com.project.command.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.commandFactory();
        Command command = commandFactory.getCommand(req);
        String page = command.execute(req, resp);
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        if (!page.equals("redirect")) {
            dispatcher.forward(req, resp);
        }
    }
}