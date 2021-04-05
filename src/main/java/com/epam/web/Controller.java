package com.epam.web;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.service.ServiceException;
import com.epam.web.util.CookieHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";
    private static final String PAGE_REQUEST_REGEX = ".*Page";

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private final CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        testMethod(request);
        saveParams(request, response);
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        testMethod(request);
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
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private void saveParams(HttpServletRequest request, HttpServletResponse response) {

        String query = request.getQueryString();
        CookieHandler cookieHandler = new CookieHandler();
        cookieHandler.setUnexpiring(response, PREVIOUS_PARAMS_COOKIE_NAME, query);
    }

    private void testMethod(HttpServletRequest request) throws ServletException {

        String command = (String) request.getAttribute("command");

        if ("GET".equals(request.getMethod())) {
            if (!command.matches(PAGE_REQUEST_REGEX)) {
                throw new ServletException("Invalid method for command: " + command);
            }
        }

        if ("POST".equals(request.getMethod())) {
            if (command.matches(PAGE_REQUEST_REGEX)) {
                throw new ServletException("Invalid method for command: " + command);
            }
        }
    }
}
