package com.epam.web.dao;

import com.epam.web.connection.ProxyConnection;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final ProxyConnection connection;

    public DaoHelper(ProxyConnection connection) {
        this.connection = connection;
    }

    public UserDao createUserDao() {
        return new UserDao(connection);
    }

    public RoomDao createRoomDao() {
        return new RoomDao(connection);
    }

    public RoomReservationDao createRoomReservationDao() {
        return new RoomReservationDao(connection);
    }

    public PaymentDao createPaymentDao() {
        return new PaymentDao(connection);
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void endTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        connection.setAutoCommit(true);
        connection.close();
    }
}
