package com.epam.web.validation;

import java.util.Set;

public class ValidationException extends Exception {

    private final Set<String> errors;

    public ValidationException(Set<String> errors) {
        this.errors = errors;
    }

    public Set<String> getErrors() {
        return errors;
    }
}
