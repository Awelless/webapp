package com.epam.web;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private final CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        saveParams(request, response);
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandType = (String) request.getAttribute("command");
        Command command = commandFactory.create(commandType);

        try {
            CommandResult commandResult = command.execute(request, response);
            String page = commandResult.getPage();
            boolean redirect = commandResult.isRedirect();

            if (redirect) {
                response.sendRedirect(page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }

        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private void saveParams(HttpServletRequest request, HttpServletResponse response) {

    }
}
