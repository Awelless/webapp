package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllReservationsRageCommand implements Command {

    private final RoomReservationService roomReservationService;

    public ShowAllReservationsRageCommand(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<RoomReservation> reservations = roomReservationService.getAll();

        request.setAttribute("reservations", reservations);

        return CommandResult.forward(Pages.ALL_RESERVATIONS);
    }
}
