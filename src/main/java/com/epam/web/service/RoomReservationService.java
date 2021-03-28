package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.PaymentDao;
import com.epam.web.dao.RoomReservationDao;
import com.epam.web.entity.*;

import java.util.Date;
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

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void approve(RoomReservation reservation, Room room) throws ServiceException {

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

        reservation.setStatus(RoomReservationStatus.REJECTED);
        save(reservation);
    }

    public void pay(RoomReservation reservation) throws ServiceException {

        reservation.setStatus(RoomReservationStatus.PAID);
        Payment payment = new Payment(reservation.getId());

        try(DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();
            PaymentDao paymentDao = daoHelper.createPaymentDao();

            daoHelper.beginTransaction();

            roomReservationDao.save(reservation);
            paymentDao.save(payment);

            daoHelper.commit();

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void decline(RoomReservation reservation) throws ServiceException {

        reservation.setStatus(RoomReservationStatus.DECLINED);
        save(reservation);
    }

    private void save(RoomReservation reservation) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            RoomReservationDao roomReservationDao = daoHelper.createRoomReservationDao();

            roomReservationDao.save(reservation);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
