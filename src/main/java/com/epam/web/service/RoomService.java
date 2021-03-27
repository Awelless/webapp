package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.RoomDao;
import com.epam.web.entity.Room;

import java.util.Date;
import java.util.List;

public class RoomService {

    private final DaoHelperFactory daoHelperFactory;

    public RoomService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public List<Room> getAvailableByDateAndBedsAndRating(Date checkIn, Date checkOut,
            int numberOfBeds, int rating) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomDao roomDao = daoHelper.createRoomDao();
            return roomDao.findAvailableByDateAndBedsAndRating(
                    checkIn, checkOut, numberOfBeds, rating);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
