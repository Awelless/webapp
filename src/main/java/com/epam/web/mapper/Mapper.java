package com.epam.web.mapper;

import com.epam.web.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T extends Entity> {

    T map(ResultSet resultSet) throws SQLException;
}
