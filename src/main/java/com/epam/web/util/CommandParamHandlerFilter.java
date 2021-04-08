package com.epam.web.util;

import com.epam.web.command.Commands;
import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class CommandParamHandlerFilter implements Filter {

    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";

    private CookieHandler cookieHandler;

    @Override
    public void init(FilterConfig filterConfig) {
        cookieHandler = new CookieHandler();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            String command = httpServletRequest.getParameter("command");

            if (Commands.PREVIOUS_REQUEST.equals(command)) {

                Optional<Cookie> optionalCookie = cookieHandler.getByName(
                        httpServletRequest, PREVIOUS_PARAMS_COOKIE_NAME);

                command = optionalCookie.map(Cookie::getValue).orElse(null);
            }

            String requestCommand;

            if (command == null || command.trim().isEmpty()) {

                User user = (User) httpServletRequest.getSession().getAttribute("user");

                if (user == null) {
                    requestCommand = Commands.LOGIN_PAGE;
                } else if (UserRole.ADMIN.equals(user.getRole())) {
                    requestCommand = Commands.ALL_RESERVATIONS_PAGE;
                } else {
                    requestCommand = Commands.NEW_RESERVATION_PAGE;
                }

            } else {
                requestCommand = command;
            }

            httpServletRequest.setAttribute("command", requestCommand);
        }

        filterChain.doFilter(request, response);
    }
}
