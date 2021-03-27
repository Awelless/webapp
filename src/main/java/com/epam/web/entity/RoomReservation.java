package com.epam.web.entity;

import java.util.Date;

public class RoomReservation implements Entity {

    private Long id;
    private Long userId;
    private Long roomId;
    private Date checkIn;
    private Date checkOut;
    private int numberOfBeds;
    private int rating;
    private long cost;
    private RoomReservationStatus status;

    public RoomReservation(Long userId, Date checkIn, Date checkOut, int numberOfBeds, int rating) {
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfBeds = numberOfBeds;
        this.rating = rating;
    }

    public RoomReservation(Long id, Long userId, Long roomId, Date checkIn, Date checkOut, int numberOfBeds, int rating, long cost, RoomReservationStatus status) {
        this(userId, checkIn, checkOut, numberOfBeds, rating);

        this.id = id;
        this.roomId = roomId;
        this.cost = cost;
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public RoomReservationStatus getStatus() {
        return status;
    }

    public void setStatus(RoomReservationStatus status) {
        this.status = status;
    }
}
