package com.epam.web.mapper;

import com.epam.web.entity.RoomReservation;
import com.epam.web.entity.RoomReservationStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomReservationMapper implements Mapper<RoomReservation> {

    @Override
    public RoomReservation map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        long userId = resultSet.getLong("user_id");
        long roomIdValue = resultSet.getLong("room_id");
        Long roomId = resultSet.wasNull() ? null : roomIdValue;
        Date checkIn = resultSet.getDate("check_in");
        Date checkOut = resultSet.getDate("check_out");
        int numberOfBeds = resultSet.getInt("number_of_beds");
        int rating = resultSet.getInt("rating");
        long costValue = resultSet.getLong("cost");
        Long cost = resultSet.wasNull() ? null : costValue;
        String statusValue = resultSet.getString("status");
        RoomReservationStatus status = RoomReservationStatus.valueOf(statusValue);

        return new RoomReservation(id, userId, roomId, checkIn, checkOut, numberOfBeds, rating, cost, status);
    }
}
