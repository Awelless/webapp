package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;
import com.epam.web.entity.Room;
import com.epam.web.mapper.RoomMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RoomDao extends AbstractDao<Room> {

    private static final String TABLE_NAME = "room";

    private static final String FIND_AVAILABLE_BY_DATE_AND_BEDS_AND_RATING =
            "select r.* from room r " +
            "left join room_reservation rr on " +
                "r.id = rr.room_id " +                           //params:
                "and (? between rr.check_in and rr.check_out " + //dateFrom
                  "or ? between rr.check_in and rr.check_out " + //dateTo
                  "or rr.check_in between ? and ?) " +           //dateFrom, dateTo
                "and rr.status <> 'REJECTED' " +                 //
                "and rr.status <> 'DECLINED' " +                 //
            "where " +                                           //
                "rr.room_id is null " +                          //
                    "and r.number_of_beds = ? " +                //numberOfBeds
                    "and r.rating = ? ";                         //rating
    private static final String CREATE = "insert into room " +
            "(id, number_of_beds, cost, rating) values (?, ?, ?, ?)";
    private static final String UPDATE = "update room " +
            "set number_of_beds = ?, cost = ?, rating = ? where id = ?";

    public RoomDao(ProxyConnection connection) {
        super(connection, new RoomMapper(), TABLE_NAME);
    }

    public List<Room> findAvailableByDateAndBedsAndRating(Date dateFrom, Date dateTo,
                                                          int numberOfBeds, int rating) throws DaoException {

        //dateFrom and dateTo are used 2 times in query
        return executeQuery(FIND_AVAILABLE_BY_DATE_AND_BEDS_AND_RATING,
                dateFrom, dateTo, dateFrom, dateTo, numberOfBeds, rating);
    }

    @Override
    protected void create(Room room) throws DaoException {
        executeUpdate(CREATE, room.getNumberOfBeds(), room.getCost(), room.getRating());
    }

    @Override
    protected void update(Room room) throws DaoException {

        Optional<Room> optionalRoom = findById(room.getId());

        if (!optionalRoom.isPresent()) {
            throw new DaoException("Room doesn't exist in table. Id is invalid: " + room.getId());
        }

        executeUpdate(UPDATE, room.getNumberOfBeds(), room.getCost(), room.getRating(), room.getId());
    }
}
