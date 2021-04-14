package com.epam.web.validation;

import com.epam.web.entity.RoomReservationDto;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RoomReservationValidatorTest {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final RoomReservationValidator validator = new RoomReservationValidator(dateFormat);

    @Test
    public void testValidateShouldValidateWhenCorrectApplied() throws ValidationException {

        RoomReservationDto reservationDto = new RoomReservationDto(
                "1", "4", "2100-01-01", "2100-01-07");

        validator.validate(reservationDto);
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionWhenInvalidValuesApplied() throws ValidationException {

        RoomReservationDto reservationDto = new RoomReservationDto(
                "", "", "2100-01-01", "2100-01-07");

        validator.validate(reservationDto);
    }

    @Test(expected = ValidationException.class)
    public void testValidateShouldThrowExceptionWhenIncorrectDateApplied() throws ValidationException {

        RoomReservationDto reservationDto = new RoomReservationDto(
                "1", "4", "2020-01-01", "2100-01-07");

        validator.validate(reservationDto);
    }
}