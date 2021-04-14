package com.epam.web.validation;

import com.epam.web.entity.User;
import com.epam.web.entity.UserCredentialsDto;
import com.epam.web.service.ServiceException;
import com.epam.web.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserCredentialsValidatorTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserCredentialsValidator validator = new UserCredentialsValidator(userService);

    @Before
    public void init() throws ServiceException {
        Mockito.when(userService.getByUsername("test")).thenReturn(Optional.empty());
        Mockito.when(userService.getByUsername("notunique")).thenReturn(Optional.of(new User("notunique", "test")));
    }

    @Test
    public void testValidateShouldValidateWhenCorrectApplied() throws ValidationException, ServiceException {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(
                "test", "test", "test");

        validator.validate(userCredentialsDto);
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionWhenInvalidPasswordApplied() throws ValidationException, ServiceException {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(
                "test", "", "test");

        validator.validate(userCredentialsDto);
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionWhenPasswordNotConfirmed() throws ValidationException, ServiceException {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(
                "test", "test", "test123");

        validator.validate(userCredentialsDto);
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionWhenUsernameNotUnique() throws ValidationException, ServiceException {

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(
                "notunique", "test", "test");

        validator.validate(userCredentialsDto);
    }
}
