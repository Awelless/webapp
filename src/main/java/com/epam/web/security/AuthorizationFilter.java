package com.epam.web.security;

import com.epam.web.command.Commands;
import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthorizationFilter implements Filter {

    private Map<String, Set<UserRole>> permissions;

    @Override
    public void init(FilterConfig filterConfig) {

        Map<String, Set<UserRole>> permissions = new HashMap<>();

        permissions.put(Commands.LOGIN,
                new HashSet<>(Collections.singletonList(UserRole.ANONYMOUS)));
        permissions.put(Commands.REGISTRATION,
                new HashSet<>(Collections.singletonList(UserRole.ANONYMOUS)));
        permissions.put(Commands.CHANGE_LOCALIZATION,
                new HashSet<>(Arrays.asList(UserRole.ANONYMOUS, UserRole.USER, UserRole.ADMIN)));
        permissions.put(Commands.LOGIN_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.ANONYMOUS)));
        permissions.put(Commands.REGISTRATION_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.ANONYMOUS)));
        permissions.put(Commands.LOGOUT,
                new HashSet<>(Arrays.asList(UserRole.USER, UserRole.ADMIN)));
        permissions.put(Commands.RESERVE_ROOM,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.RESERVATION_PAY,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.RESERVATION_CANCEL,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.NEW_RESERVATION_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.NEW_RESERVATION_SUCCESS_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.USER_RESERVATIONS_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.PAYMENT_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.USER)));
        permissions.put(Commands.RESERVATION_APPROVE,
                new HashSet<>(Collections.singletonList(UserRole.ADMIN)));
        permissions.put(Commands.RESERVATION_REJECT,
                new HashSet<>(Collections.singletonList(UserRole.ADMIN)));
        permissions.put(Commands.ALL_RESERVATIONS_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.ADMIN)));
        permissions.put(Commands.CHOOSING_ROOM_PAGE,
                new HashSet<>(Collections.singletonList(UserRole.ADMIN)));

        this.permissions = Collections.unmodifiableMap(permissions);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String command = (String) httpServletRequest.getAttribute("command");

            User user = (User) httpServletRequest.getSession().getAttribute("user");
            UserRole role = user != null ? user.getRole() : UserRole.ANONYMOUS;

            boolean authorized = permissions.containsKey(command) && permissions.get(command).contains(role);

            if (!authorized) {
                httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        permissions = null;
    }
}
