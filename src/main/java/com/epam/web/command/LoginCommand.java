package com.epam.web.command;

import com.epam.web.entity.User;
import com.epam.web.service.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> optionalUser = userService.login(username, password);

        if (optionalUser.isPresent()) {
            request.getSession().setAttribute("user", optionalUser.get());
            return CommandResult.redirect(request.getRequestURI() + Params.NEW_RESERVATION_PAGE);
        }

        request.setAttribute("badCredentials", true);
        request.setAttribute("username", username);
        return CommandResult.forward(Pages.LOGIN);
    }
}
