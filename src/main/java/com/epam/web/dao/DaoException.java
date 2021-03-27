package com.epam.web.dao;

public class DaoException extends Exception {

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String s) {
        super(s);
    }
}
