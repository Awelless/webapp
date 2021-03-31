package com.epam.web.command;

import com.epam.web.entity.User;
import com.epam.web.service.ServiceException;
import com.epam.web.service.ServiceFactory;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationCommand implements Command {

    private String username;
    private String password;
    private String passwordConfirmation;

    private final UserService userService;

    public RegistrationCommand(ServiceFactory serviceFactory) {
        this.userService = serviceFactory.createUserService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        username = request.getParameter("username");
        password = request.getParameter("password");
        passwordConfirmation = request.getParameter("passwordConfirmation");

        if (!validate(request)) {
            request.setAttribute("username", username);
            return CommandResult.forward(Pages.REGISTRATION);
        }

        userService.create(username, password);

        return CommandResult.redirect(request.getRequestURI() + Params.LOGIN_PAGE);
    }

    private boolean validate(HttpServletRequest request) throws ServiceException {

        boolean valid = true;

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", true);
            valid = false;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", true);
            valid = false;
        }

        if (passwordConfirmation == null || !passwordConfirmation.equals(password)) {
            request.setAttribute("passwordConfirmationError", true);
            valid = false;
        }

        if (username != null) {
            Optional<User> optionalUser = userService.getByUsername(username);

            if (optionalUser.isPresent()) {
                request.setAttribute("uniquenessError", true);
                valid = false;
            }
        }

        return valid;
    }
}
