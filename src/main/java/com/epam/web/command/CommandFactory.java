package com.epam.web.command;

import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.UserService;

public class CommandFactory {

    private final DaoHelperFactory daoHelperFactory = new DaoHelperFactory();

    public Command create(String type) {

        if (type == null) {
            return new ShowPageCommand(Pages.LOGIN);
        }

        switch (type) {
            case "login":
                return new LoginCommand(new UserService(daoHelperFactory));
            case "logout":
                return new LogoutCommand();
            case "registration":
                return new RegistrationCommand(new UserService(daoHelperFactory));
            case "localization":
                return new ChangeLocalizationCommand();
            case "reservation":
                return new ReserveRoomCommand(new RoomReservationService(daoHelperFactory));
            case "loginPage":
                return new ShowPageCommand(Pages.LOGIN);
            case "registrationPage":
                return new ShowPageCommand(Pages.REGISTRATION);
            case "newReservationPage":
                return new ShowPageCommand(Pages.ROOM_RESERVATION);
            case "newReservationSuccessPage":
                return new ShowPageCommand(Pages.ROOM_RESERVATION_SUCCESS);
            case "userReservationsPage":
                return new ShowPageCommand(Pages.USER_RESERVATIONS);
            case "paymentPage":
                return new ShowPageCommand(Pages.PAYMENT);
            case "allReservationsPage":
                return new ShowPageCommand(Pages.ALL_RESERVATIONS);
            case "choosingRoomPage":
                return new ShowPageCommand(Pages.CHOOSING_ROOM);
            default:
                throw new IllegalArgumentException("Unknown type of Command " + type);
        }
    }
}
