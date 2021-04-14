package com.epam.web.command;

import com.epam.web.entity.RoomReservationDto;
import com.epam.web.entity.User;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;
import com.epam.web.validation.RoomReservationValidator;
import com.epam.web.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class ReserveRoomCommand implements Command {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final RoomReservationService roomReservationService;
    private final RoomReservationValidator roomReservationValidator;
    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public ReserveRoomCommand(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
        this.roomReservationValidator = new RoomReservationValidator(dateFormat);
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        User user = (User) request.getSession().getAttribute("user");

        String numberOfBedsValue = request.getParameter("numberOfBeds");
        String ratingValue = request.getParameter("rating");
        String checkInValue = request.getParameter("checkIn");
        String checkOutValue = request.getParameter("checkOut");

        RoomReservationDto roomReservationDto = new RoomReservationDto(
                numberOfBedsValue, ratingValue, checkInValue, checkOutValue);

        try {
            roomReservationValidator.validate(roomReservationDto);

        } catch (ValidationException e) {

            request.setAttribute("numberOfBeds", numberOfBedsValue);
            request.setAttribute("rating", ratingValue);
            request.setAttribute("checkIn", checkInValue);
            request.setAttribute("checkOut", checkOutValue);

            Set<String> errors = e.getErrors();
            errors.forEach(error -> request.setAttribute(error, true));

            return CommandResult.forward(Pages.ROOM_RESERVATION);
        }

        try {
            int numberOfBeds = Integer.parseInt(numberOfBedsValue);
            int rating = Integer.parseInt(ratingValue);
            Date checkIn = dateFormat.parse(checkInValue);
            Date checkOut = dateFormat.parse(checkOutValue);

            roomReservationService.create(user, numberOfBeds, rating, checkIn, checkOut);

        } catch (ParseException e) {
            //data is already validated, so this block will never be executed ... ignored
        }

        return CommandResult.redirect(request.getRequestURI() + "?command=" + Commands.NEW_RESERVATION_SUCCESS_PAGE);
    }
}
