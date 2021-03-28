package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;
import com.epam.web.entity.User;
import com.epam.web.mapper.UserMapper;

import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final String TABLE_NAME = "user";

    private static final String FIND_BY_USERNAME = "select * from user where binary username = ?";
    private static final String FIND_BY_USERNAME_AND_PASSWORD = "select * from user where binary username = ? and binary password = md5(?)";
    private static final String CREATE = "insert into user (username, password, role) values (?, md5(?), ?)";
    private static final String UPDATE = "update user set username = ?, password = md5(?), role = ? where id = ?";

    public UserDao(ProxyConnection connection) {
        super(connection, new UserMapper(), TABLE_NAME);
    }

    public Optional<User> findByUsername(String username) throws DaoException {
        return executeSingleResultQuery(FIND_BY_USERNAME, username);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) throws DaoException {
        return executeSingleResultQuery(FIND_BY_USERNAME_AND_PASSWORD, username, password);
    }

    @Override
    protected void create(User user) throws DaoException {
        executeUpdate(CREATE, user.getUsername(), user.getPassword(), user.getRole().toString());
    }

    @Override
    protected void update(User user) throws DaoException {

        Optional<User> optionalUser = findById(user.getId());

        if (!optionalUser.isPresent()) {
            throw new DaoException("User doesn't exist in table. Id is invalid: " + user.getId());
        }

        executeUpdate(UPDATE, user.getUsername(), user.getPassword(), user.getRole().toString(), user.getId());
    }
}
