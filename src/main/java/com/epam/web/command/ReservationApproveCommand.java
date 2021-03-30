package com.epam.web.command;

import com.epam.web.entity.Room;
import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.RoomService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ReservationApproveCommand implements Command {

    private final RoomReservationService roomReservationService;
    private final RoomService roomService;

    public ReservationApproveCommand(RoomReservationService roomReservationService, RoomService roomService) {
        this.roomReservationService = roomReservationService;
        this.roomService = roomService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        long reservationId = Long.parseLong(request.getParameter("reservationId"));
        long roomId = Long.parseLong(request.getParameter("roomId"));

        Optional<RoomReservation> reservation = roomReservationService.getById(reservationId);
        Optional<Room> room = roomService.getById(roomId);

        if (reservation.isPresent() && room.isPresent()) {
            roomReservationService.approve(reservation.get(), room.get());
        }

        return CommandResult.redirect(Pages.USER_RESERVATIONS);
    }
}
