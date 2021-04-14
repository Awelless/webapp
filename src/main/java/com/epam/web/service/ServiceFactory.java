package com.epam.web.service;

import com.epam.web.dao.DaoHelperFactory;

public class ServiceFactory {

    private final DaoHelperFactory daoHelperFactory = new DaoHelperFactory();

    public RoomReservationService createRoomReservationService() {
        return new RoomReservationService(daoHelperFactory);
    }

    public UserService createUserService() {
        return new UserService(daoHelperFactory);
    }

    public RoomService createRoomService() {
        return new RoomService(daoHelperFactory);
    }
}
