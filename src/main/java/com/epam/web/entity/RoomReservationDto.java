package com.epam.web.entity;

public class RoomReservationDto {

    private final String numberOfBedsValue;
    private final String ratingValue;
    private final String checkInValue;
    private final String checkOutValue;

    public RoomReservationDto(String numberOfBedsValue, String ratingValue, String checkInValue, String checkOutValue) {
        this.numberOfBedsValue = numberOfBedsValue;
        this.ratingValue = ratingValue;
        this.checkInValue = checkInValue;
        this.checkOutValue = checkOutValue;
    }

    public String getNumberOfBedsValue() {
        return numberOfBedsValue;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public String getCheckInValue() {
        return checkInValue;
    }

    public String getCheckOutValue() {
        return checkOutValue;
    }
}
