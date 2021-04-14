package com.epam.web.mapper;

import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String roleValue = resultSet.getString("role");
        UserRole role = UserRole.valueOf(roleValue);

        return new User(id, username, password, role);
    }
}
