package com.epam.web.entity;

public class Payment implements Entity {

    private Long id;
    private Long roomReservationId;

    public Payment(Long roomReservationId) {
        this.roomReservationId = roomReservationId;
    }

    public Payment(Long id, Long roomReservationId) {
        this(roomReservationId);

        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomReservationId() {
        return roomReservationId;
    }

    public void setRoomReservationId(Long roomReservationId) {
        this.roomReservationId = roomReservationId;
    }
}
