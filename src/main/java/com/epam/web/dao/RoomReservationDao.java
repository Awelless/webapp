package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;
import com.epam.web.entity.RoomReservation;
import com.epam.web.mapper.RoomReservationMapper;

import java.util.List;
import java.util.Optional;

public class RoomReservationDao extends AbstractDao<RoomReservation> {

    private static final String TABLE_NAME = "room_reservation";

    private static final String FIND_BY_USER_ID = "select * from room_reservation where user_id = ? order by id desc";
    private static final String CREATE = "insert into room_reservation " +
            "(user_id, room_id, check_in, check_out, number_of_beds, rating, cost, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "update room_reservation " +
            "set user_id = ?, room_id = ?, check_in = ?, check_out = ?, " +
            "number_of_beds = ?, rating = ?, cost = ?, status = ? where id = ?";

    public RoomReservationDao(ProxyConnection connection) {
        super(connection, new RoomReservationMapper(), TABLE_NAME);
    }

    public List<RoomReservation> findByUserId(long userId) throws DaoException {
        return executeQuery(FIND_BY_USER_ID, userId);
    }

    @Override
    protected void create(RoomReservation roomReservation) throws DaoException {
        executeUpdate(CREATE,
                roomReservation.getUserId(),
                roomReservation.getRoomId(),
                roomReservation.getCheckIn(),
                roomReservation.getCheckOut(),
                roomReservation.getNumberOfBeds(),
                roomReservation.getRating(),
                roomReservation.getCost(),
                roomReservation.getStatus().toString());
    }

    @Override
    protected void update(RoomReservation roomReservation) throws DaoException {

        Optional<RoomReservation> optionalRoomReservation = findById(roomReservation.getId());

        if (!optionalRoomReservation.isPresent()) {
            throw new DaoException("RoomReservation doesn't exist in table. Id is invalid: " + roomReservation.getId());
        }

        executeUpdate(UPDATE,
                roomReservation.getUserId(),
                roomReservation.getRoomId(),
                roomReservation.getCheckIn(),
                roomReservation.getCheckOut(),
                roomReservation.getNumberOfBeds(),
                roomReservation.getRating(),
                roomReservation.getCost(),
                roomReservation.getStatus().toString(),
                roomReservation.getId());
    }
}
