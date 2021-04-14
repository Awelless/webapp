package com.epam.web.service;

public class ServiceException extends Exception {

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String s) {
        super(s);
    }
}
