package com.epam.web.security;

import com.epam.web.command.Commands;
import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            User user = (User) httpServletRequest.getSession().getAttribute("user");

            if (user != null) {

                boolean authorized;

                if (UserRole.ADMIN.equals(user.getRole())) {
                    authorized = hasAdminCommand(httpServletRequest);
                } else {
                    authorized = hasUserCommand(httpServletRequest);
                }

                if (!authorized) {
                    httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    boolean hasUserCommand(HttpServletRequest request) {

        String command = (String) request.getAttribute("command");

        return Commands.LOGOUT.equals(command) ||
                Commands.CHANGE_LOCALIZATION.equals(command) ||
                Commands.RESERVE_ROOM.equals(command) ||
                Commands.RESERVATION_PAY.equals(command) ||
                Commands.RESERVATION_CANCEL.equals(command) ||
                Commands.NEW_RESERVATION_PAGE.equals(command) ||
                Commands.NEW_RESERVATION_SUCCESS_PAGE.equals(command) ||
                Commands.USER_RESERVATIONS_PAGE.equals(command) ||
                Commands.PAYMENT_PAGE.equals(command);
    }

    boolean hasAdminCommand(HttpServletRequest request) {

        String command = (String) request.getAttribute("command");

        return Commands.LOGOUT.equals(command) ||
                Commands.CHANGE_LOCALIZATION.equals(command) ||
                Commands.RESERVATION_APPROVE.equals(command) ||
                Commands.RESERVATION_REJECT.equals(command) ||
                Commands.ALL_RESERVATIONS_PAGE.equals(command) ||
                Commands.CHOOSING_ROOM_PAGE.equals(command);
    }
}
