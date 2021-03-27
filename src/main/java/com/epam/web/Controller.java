package com.epam.web;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.service.ServiceException;
import com.epam.web.util.CookieHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";

    private final CommandFactory commandFactory = new CommandFactory();
    private final CookieHandler cookieHandler = new CookieHandler();

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

        String commandType = request.getParameter("command");
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
            // ...
        }
    }

    private void saveParams(HttpServletRequest request, HttpServletResponse response) {

        String query = request.getQueryString();
        Cookie cookie = new Cookie(PREVIOUS_PARAMS_COOKIE_NAME, query);
        cookieHandler.set(response, cookie);
    }
}
