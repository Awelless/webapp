package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ShowPaymentPageCommand implements Command {

    private final RoomReservationService roomReservationService;

    public ShowPaymentPageCommand(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        long id = Long.parseLong(request.getParameter("reservationId"));
        Optional<RoomReservation> reservationOptional = roomReservationService.getById(id);

        reservationOptional.ifPresent(reservation -> request.setAttribute("reservation", reservation));

        return CommandResult.forward(Pages.PAYMENT);
    }
}
