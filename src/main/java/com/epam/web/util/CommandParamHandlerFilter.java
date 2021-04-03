package com.epam.web.util;

import com.epam.web.command.Commands;
import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CommandParamHandlerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            String requestCommand;
            String command = httpServletRequest.getParameter("command");

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
