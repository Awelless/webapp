package com.epam.web.command;

import com.epam.web.entity.Room;
import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.RoomService;
import com.epam.web.service.ServiceException;
import com.epam.web.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowChoosingRoomPageCommand implements Command {

    private final RoomReservationService roomReservationService;
    private final RoomService roomService;

    public ShowChoosingRoomPageCommand(ServiceFactory serviceFactory) {
        this.roomReservationService = serviceFactory.createRoomReservationService();
        this.roomService = serviceFactory.createRoomService();
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
