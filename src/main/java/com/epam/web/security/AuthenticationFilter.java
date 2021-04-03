package com.epam.web.security;

import com.epam.web.command.Commands;
import com.epam.web.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            User user = (User) httpServletRequest.getSession().getAttribute("user");

            if (user == null) {

                if (!hasAnonymousCommand(httpServletRequest)) {
                    httpServletResponse.sendRedirect(httpServletRequest.getRequestURI() + "?command=" + Commands.LOGIN_PAGE);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    boolean hasAnonymousCommand(HttpServletRequest request) {

        String command = (String) request.getAttribute("command");

        return Commands.LOGIN.equals(command) ||
                Commands.REGISTRATION.equals(command) ||
                Commands.CHANGE_LOCALIZATION.equals(command) ||
                Commands.LOGIN_PAGE.equals(command) ||
                Commands.REGISTRATION_PAGE.equals(command);
    }
}
