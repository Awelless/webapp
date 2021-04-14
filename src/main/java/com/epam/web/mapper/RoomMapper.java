package com.epam.web.mapper;

import com.epam.web.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements Mapper<Room> {

    @Override
    public Room map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        int numberOfBeds = resultSet.getInt("number_of_beds");
        int cost = resultSet.getInt("cost");
        int rating = resultSet.getInt("rating");

        return new Room(id, numberOfBeds, cost, rating);
    }
}
