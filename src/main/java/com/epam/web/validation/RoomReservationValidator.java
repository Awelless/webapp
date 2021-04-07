package com.epam.web.validation;

import com.epam.web.entity.RoomReservationDto;
import com.epam.web.service.ServiceException;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RoomReservationValidator implements Validator<RoomReservationDto> {

    private final DateFormat dateFormat;

    public RoomReservationValidator(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void validate(RoomReservationDto reservation) throws ValidationException, ServiceException {

        String numberOfBedsValue = reservation.getNumberOfBedsValue();
        String ratingValue = reservation.getRatingValue();
        String checkInValue = reservation.getCheckInValue();
        String checkOutValue = reservation.getCheckOutValue();

        Set<String> errors = new HashSet<>();

        if (numberOfBedsValue == null || numberOfBedsValue.isEmpty()) {
            errors.add("bedsError");
        }

        if (ratingValue == null || ratingValue.isEmpty()) {
            errors.add("ratingError");
        }

        if (checkInValue == null || checkInValue.isEmpty() ||
                checkOutValue == null || checkOutValue.isEmpty()) {
            errors.add("dateError");

        } else {
            try {
                Date checkIn = dateFormat.parse(checkInValue);
                Date checkOut = dateFormat.parse(checkOutValue);

                if (checkOut.before(checkIn) || checkIn.before(new Date())) {
                    errors.add("dateError");
                }

            } catch (ParseException e) {
                errors.add("dateError");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
