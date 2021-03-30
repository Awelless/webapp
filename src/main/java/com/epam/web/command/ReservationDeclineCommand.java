package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ReservationDeclineCommand implements Command {

    private final RoomReservationService roomReservationService;

    public ReservationDeclineCommand(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        long reservationId = Long.parseLong(request.getParameter("reservationId"));

        Optional<RoomReservation> reservation = roomReservationService.getById(reservationId);

        if (reservation.isPresent()) {
            roomReservationService.decline(reservation.get());
        }

        return CommandResult.redirect(Pages.USER_RESERVATIONS);
    }
}
