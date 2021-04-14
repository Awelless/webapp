package com.epam.web.validation;

import com.epam.web.service.ServiceException;

public interface Validator<T> {

    void validate(T object) throws ValidationException, ServiceException;
}
