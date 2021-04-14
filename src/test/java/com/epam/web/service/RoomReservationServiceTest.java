package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Matchers.any;

public class RoomReservationServiceTest {

    private final RoomReservationDao roomReservationDao = Mockito.mock(RoomReservationDao.class);
    private final PaymentDao paymentDao = Mockito.mock(PaymentDao.class);
    private final DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
    private final DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);

    private final RoomReservationService service = new RoomReservationService(daoHelperFactory);

    @Before
    public void init() throws DaoException, SQLException {
        Mockito.doNothing().when(roomReservationDao).save(any(RoomReservation.class));
        Mockito.doNothing().when(paymentDao).save(any(Payment.class));

        Mockito.when(daoHelperFactory.create()).thenReturn(daoHelper);
        Mockito.when(daoHelper.createRoomReservationDao()).thenReturn(roomReservationDao);
        Mockito.when(daoHelper.createPaymentDao()).thenReturn(paymentDao);
        Mockito.doNothing().when(daoHelper).beginTransaction();
        Mockito.doNothing().when(daoHelper).endTransaction();
        Mockito.doNothing().when(daoHelper).commit();
        Mockito.doNothing().when(daoHelper).close();
        Mockito.doNothing().when(daoHelper).rollback();
    }

    @Test
    public void testCreateShouldCreate() throws ServiceException, DaoException {

        User user = new User(1L, "123", "asdj", UserRole.USER);

        service.create(user, 1, 5, new Date(), new Date());

        Mockito.verify(roomReservationDao, Mockito.times(1)).save(any(RoomReservation.class));
    }

    @Test
    public void testApproveShouldApprove() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);
        Room room = new Room(2L, 2, 20, 2);

        service.approve(reservation, room);

        Mockito.verify(roomReservationDao, Mockito.times(1)).save(any(RoomReservation.class));
    }

    @Test(expected = ServiceException.class)
    public void testApproveShouldThrowExceptionWhenInvalidReservationApplied() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);
        reservation.setStatus(RoomReservationStatus.REJECTED);
        Room room = new Room(2L, 2, 20, 2);

        service.approve(reservation, room);
    }

    @Test
    public void testRejectShouldReject() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);

        service.reject(reservation);

        Mockito.verify(roomReservationDao, Mockito.times(1)).save(any(RoomReservation.class));
    }

    @Test(expected = ServiceException.class)
    public void testRejectShouldThrowExceptionWhenInvalidReservationApplied() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);
        reservation.setStatus(RoomReservationStatus.REJECTED);

        service.reject(reservation);
    }

    @Test
    public void testPayShouldPay() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);
        reservation.setStatus(RoomReservationStatus.APPROVED);

        service.pay(reservation);

        Mockito.verify(roomReservationDao, Mockito.times(1)).save(any(RoomReservation.class));
        Mockito.verify(paymentDao, Mockito.times(1)).save(any(Payment.class));
    }

    @Test(expected = ServiceException.class)
    public void testPayShouldThrowExceptionWhenInvalidReservationApplied() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);

        service.pay(reservation);
    }

    @Test
    public void testCancelShouldCancel() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);

        service.cancel(reservation);

        Mockito.verify(roomReservationDao, Mockito.times(1)).save(any(RoomReservation.class));
    }

    @Test(expected = ServiceException.class)
    public void testCancelShouldThrowExceptionWhenInvalidReservationApplied() throws ServiceException, DaoException {

        RoomReservation reservation = new RoomReservation(1L, new Date(), new Date(), 1, 5);
        reservation.setStatus(RoomReservationStatus.CANCELED);

        service.cancel(reservation);
    }
}
