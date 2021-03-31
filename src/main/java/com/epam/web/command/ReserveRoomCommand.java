package com.epam.web.command;

import com.epam.web.entity.User;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;
import com.epam.web.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserveRoomCommand implements Command {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final RoomReservationService roomReservationService;
    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    private String numberOfBedsValue;
    private String ratingValue;
    private String checkInValue;
    private String checkOutValue;
    private Date checkIn;
    private Date checkOut;

    public ReserveRoomCommand(ServiceFactory serviceFactory) {
        this.roomReservationService = serviceFactory.createRoomReservationService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        User user = (User) request.getSession().getAttribute("user");
        numberOfBedsValue = request.getParameter("numberOfBeds");
        ratingValue = request.getParameter("rating");
        checkInValue = request.getParameter("checkIn");
        checkOutValue = request.getParameter("checkOut");

        if (!validate(request)) {
            request.setAttribute("numberOfBeds", numberOfBedsValue);
            request.setAttribute("rating", ratingValue);
            request.setAttribute("checkIn", checkInValue);
            request.setAttribute("checkOut", checkOutValue);
            return CommandResult.forward(Pages.ROOM_RESERVATION);
        }

        int numberOfBeds = Integer.parseInt(numberOfBedsValue);
        int rating = Integer.parseInt(ratingValue);

        roomReservationService.create(user, numberOfBeds, rating, checkIn, checkOut);

        return CommandResult.redirect(request.getRequestURI() + Params.NEW_RESERVATION_SUCCESS_PAGE);
    }

    private boolean validate(HttpServletRequest request) {

        boolean valid = true;

        if (numberOfBedsValue == null || numberOfBedsValue.isEmpty()) {
            request.setAttribute("bedsError", true);
            valid = false;
        }

        if (ratingValue == null || ratingValue.isEmpty()) {
            request.setAttribute("ratingError", true);
            valid = false;
        }

        if (checkInValue == null || checkInValue.isEmpty() ||
                checkOutValue == null || checkOutValue.isEmpty()) {
            request.setAttribute("dateError", true);
            valid = false;

        } else {
            try {
                checkIn = dateFormat.parse(checkInValue);
                checkOut = dateFormat.parse(checkOutValue);

                if (checkOut.before(checkIn) || checkIn.before(new Date())) {
                    request.setAttribute("dateError", true);
                    valid = false;
                }

            } catch (ParseException e) {
                request.setAttribute("dateError", true);
                valid = false;
            }
        }

        return valid;
    }
}
