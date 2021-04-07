package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.entity.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RoomReservationService {

    private final DaoHelperFactory daoHelperFactory;

    public RoomReservationService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void create(User user, int numberOfBeds, int rating,
                       Date checkIn, Date checkOut) throws ServiceException {

        RoomReservation reservation = new RoomReservation(
                user.getId(), checkIn, checkOut, numberOfBeds, rating);
        save(reservation);
    }

    public Optional<RoomReservation> getById(long id) throws ServiceException {

        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();

            return roomReservationDao.findById(id);

        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void approve(RoomReservation reservation, Room room) throws ServiceException {

        if (reservation == null || !RoomReservationStatus.PENDING.equals(reservation.getStatus())) {
            throw new ServiceException("Can't approve given reservation: " + reservation);
        }

        reservation.setStatus(RoomReservationStatus.APPROVED);
        reservation.setRoomId(room.getId());

        Date checkIn = reservation.getCheckIn();
        Date checkOut = reservation.getCheckOut();
        long reservationLengthInMillis = checkOut.getTime() - checkIn.getTime();
        long reservationLengthInDays = TimeUnit.DAYS.convert(reservationLengthInMillis, TimeUnit.MILLISECONDS);
        reservation.setCost(room.getCost() * reservationLengthInDays);

        save(reservation);
    }

    public void reject(RoomReservation reservation) throws ServiceException {

        if (reservation == null || !RoomReservationStatus.PENDING.equals(reservation.getStatus())) {
            throw new ServiceException("Can't reject given reservation: " + reservation);
        }

        reservation.setStatus(RoomReservationStatus.REJECTED);
        save(reservation);
    }

    public void pay(RoomReservation reservation) throws ServiceException {

        if (reservation == null || !RoomReservationStatus.APPROVED.equals(reservation.getStatus())) {
            throw new ServiceException("Can't pay given reservation: " + reservation);
        }

        reservation.setStatus(RoomReservationStatus.PAID);
        Payment payment = new Payment(reservation.getId());

        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();
            PaymentDao paymentDao = daoHelper.createPaymentDao();

            daoHelper.beginTransaction();

            roomReservationDao.save(reservation);
            paymentDao.save(payment);

            daoHelper.commit();

        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void cancel(RoomReservation reservation) throws ServiceException {

        if (reservation == null || RoomReservationStatus.PAID.equals(reservation.getStatus())) {
            throw new ServiceException("Can't decline given reservation: " + reservation);
        }

        reservation.setStatus(RoomReservationStatus.CANCELED);
        save(reservation);
    }

    private void save(RoomReservation reservation) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();

            roomReservationDao.save(reservation);

        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<RoomReservation> getByUser(User user) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();

            return roomReservationDao.findByUserId(user.getId());

        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<RoomReservation> getAll() throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();

            return roomReservationDao.findAll();

        } catch (SQLException | DaoException e) {
            throw new ServiceException(e);
        }
    }
}
