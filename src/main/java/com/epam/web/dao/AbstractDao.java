package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;
import com.epam.web.entity.Entity;
import com.epam.web.mapper.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {

    private static final String FIND_BY_ID = "select * from %s where id = ?";
    private static final String FIND_ALL = "select * from %s";
    private static final String DELETE = "delete from %s where id = ?";

    private final ProxyConnection connection;
    private final Mapper<T> mapper;
    private final String tableName;

    public AbstractDao(ProxyConnection connection, Mapper<T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    private PreparedStatement createStatement(String query, Object ...params) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; ++i) {
            statement.setObject(i + 1, params[i]);
        }

        return statement;
    }

    protected List<T> executeQuery(String query, Object ...params) throws DaoException {

        try (PreparedStatement statement = createStatement(query, params);
             ResultSet resultSet = statement.executeQuery()) {

            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }

            return entities;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected Optional<T> executeSingleResultQuery(String query, Object ...params) throws DaoException {

        List<T> entities = executeQuery(query, params);

        if (entities.size() > 1) {
            throw new DaoException("There are more than one rows were found: " + entities.size());
        }

        if (entities.size() > 0) {
            return Optional.of(entities.get(0));
        }

        return Optional.empty();
    }

    protected void executeUpdate(String query, Object ...params) throws DaoException {

        try (PreparedStatement statement = createStatement(query, params)) {
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<T> findById(long id) throws DaoException {
        String query = String.format(FIND_BY_ID, tableName);
        return executeSingleResultQuery(query, id);
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = String.format(FIND_ALL, tableName);
        return executeQuery(query);
    }

    @Override
    public void save(T entity) throws DaoException {

        if (entity.getId() == null) {
            create(entity);
        } else {
            update(entity);
        }
    }

    protected abstract void create(T entity) throws DaoException;

    protected abstract void update(T entity) throws DaoException;

    @Override
    public void deleteById(long id) throws DaoException {
        String query = String.format(DELETE, tableName);
        executeUpdate(query, id);
    }
}
