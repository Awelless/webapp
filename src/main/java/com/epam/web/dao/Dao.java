package com.epam.web.dao;

import com.epam.web.entity.Entity;
import com.epam.web.pagination.Page;
import com.epam.web.pagination.PageRequest;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {

    Optional<T> findById(long id) throws DaoException;
    List<T> findAll() throws DaoException;
    Page<T> findAll(PageRequest pageRequest) throws DaoException;

    void save(T entity) throws DaoException;
    void deleteById(long id) throws DaoException;
}
