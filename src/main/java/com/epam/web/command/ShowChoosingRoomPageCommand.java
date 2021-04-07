package com.epam.web.command;

import com.epam.web.entity.Room;
import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.RoomService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowChoosingRoomPageCommand implements Command {

    private final RoomReservationService roomReservationService;
    private final RoomService roomService;

    public ShowChoosingRoomPageCommand(RoomReservationService roomReservationService, RoomService roomService) {
        this.roomReservationService = roomReservationService;
        this.roomService = roomService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        long id = Long.parseLong(request.getParameter("reservationId"));
        Optional<RoomReservation> reservationOptional = roomReservationService.getById(id);

        if (reservationOptional.isPresent()) {

            RoomReservation reservation = reservationOptional.get();
            request.setAttribute("reservation", reservation);

            List<Room> rooms = roomService.getAvailableByDateAndBedsAndRating(
                    reservation.getCheckIn(),
                    reservation.getCheckOut(),
                    reservation.getNumberOfBeds(),
                    reservation.getRating());

            request.setAttribute("rooms", rooms);
        }

        return CommandResult.forward(Pages.CHOOSING_ROOM);
    }
}
