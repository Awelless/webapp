package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.entity.User;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserReservationsPageCommand implements Command {

    private final RoomReservationService roomReservationService;

    public ShowUserReservationsPageCommand(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        User user = (User) request.getSession().getAttribute("user");

        List<RoomReservation> reservations = roomReservationService.getByUser(user);

        request.setAttribute("reservations", reservations);

        return CommandResult.forward(Pages.USER_RESERVATIONS);
    }
}
