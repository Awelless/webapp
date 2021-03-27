package com.epam.web.dao;

import com.epam.web.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {

    Optional<T> findById(long id) throws DaoException;
    List<T> findAll() throws DaoException;

    void save(T entity) throws DaoException;
    void deleteById(long id) throws DaoException;
}
