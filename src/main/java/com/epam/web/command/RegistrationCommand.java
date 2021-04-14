package com.epam.web.command;

import com.epam.web.entity.UserCredentialsDto;
import com.epam.web.service.ServiceException;
import com.epam.web.service.UserService;
import com.epam.web.validation.UserCredentialsValidator;
import com.epam.web.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class RegistrationCommand implements Command {

    private final UserCredentialsValidator userCredentialsValidator;
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
        this.userCredentialsValidator = new UserCredentialsValidator(userService);
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(username, password, passwordConfirmation);

        try {
            userCredentialsValidator.validate(userCredentialsDto);

        } catch (ValidationException e) {

            request.setAttribute("username", username);

            Set<String> errors = e.getErrors();
            errors.forEach(error -> request.setAttribute(error, true));

            return CommandResult.forward(Pages.REGISTRATION);
        }

        userService.create(username, password);

        return CommandResult.redirect(request.getRequestURI() + "?command=" + Commands.LOGIN_PAGE);
    }
}
