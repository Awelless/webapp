package com.epam.web.mapper;

import com.epam.web.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements Mapper<Payment> {

    @Override
    public Payment map(ResultSet resultSet) throws SQLException {

        long id = resultSet.getLong("id");
        long roomReservationId = resultSet.getLong("room_reservation_id");

        return new Payment(id, roomReservationId);
    }
}
