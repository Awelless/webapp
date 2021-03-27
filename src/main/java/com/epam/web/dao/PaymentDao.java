package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;
import com.epam.web.entity.Payment;
import com.epam.web.mapper.PaymentMapper;

import java.util.Optional;

public class PaymentDao extends AbstractDao<Payment> {

    private static final String TABLE_NAME = "payment";

    private static final String CREATE = "insert into payment (room_reservation_id) values (?)";
    private static final String UPDATE = "update payment set room_reservation_id = ? where id = ?";

    public PaymentDao(ProxyConnection connection) {
        super(connection, new PaymentMapper(), TABLE_NAME);
    }

    @Override
    protected void create(Payment payment) throws DaoException {
        executeUpdate(CREATE, payment.getRoomReservationId());
    }

    @Override
    protected void update(Payment payment) throws DaoException {

        Optional<Payment> optionalPayment = findById(payment.getId());

        if (!optionalPayment.isPresent()) {
            throw new DaoException("Payment doesn't exist in table. Id is invalid: " + payment.getId());
        }

        executeUpdate(UPDATE, payment.getRoomReservationId(), payment.getId());
    }
}
