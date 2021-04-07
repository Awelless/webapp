package com.epam.web.validation;

import com.epam.web.entity.User;
import com.epam.web.entity.UserCredentialsDto;
import com.epam.web.service.ServiceException;
import com.epam.web.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserCredentialsValidator implements Validator<UserCredentialsDto> {

    private final UserService userService;

    public UserCredentialsValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validate(UserCredentialsDto credentials) throws ValidationException, ServiceException {

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String passwordConfirmation = credentials.getPasswordConfirmation();

        Set<String> errors = new HashSet<>();

        if (username == null || username.trim().isEmpty()) {
            errors.add("usernameError");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add("passwordError");
        }

        if (passwordConfirmation == null || !passwordConfirmation.equals(password)) {
            errors.add("passwordConfirmationError");
        }

        if (username != null) {
            Optional<User> optionalUser = userService.getByUsername(username);

            if (optionalUser.isPresent()) {
                errors.add("uniquenessError");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
