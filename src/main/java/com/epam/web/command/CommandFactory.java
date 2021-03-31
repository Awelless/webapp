package com.epam.web.command;

import com.epam.web.service.ServiceFactory;

public class CommandFactory {

    private final ServiceFactory serviceFactory = new ServiceFactory();

    public Command create(String type) {

        if (type == null) {
            return new ShowPageCommand(Pages.LOGIN);
        }

        switch (type) {
            case "login":
                return new LoginCommand(serviceFactory);
            case "logout":
                return new LogoutCommand();
            case "registration":
                return new RegistrationCommand(serviceFactory);
            case "localization":
                return new ChangeLocalizationCommand();
            case "reservation":
                return new ReserveRoomCommand(serviceFactory);
            case "approve":
                return new ReservationApproveCommand(serviceFactory);
            case "reject":
                return new ReservationRejectCommand(serviceFactory);
            case "pay":
                return new ReservationPayCommand(serviceFactory);
            case "cancel":
                return new ReservationCancelCommand(serviceFactory);
            case "loginPage":
                return new ShowPageCommand(Pages.LOGIN);
            case "registrationPage":
                return new ShowPageCommand(Pages.REGISTRATION);
            case "newReservationPage":
                return new ShowPageCommand(Pages.ROOM_RESERVATION);
            case "newReservationSuccessPage":
                return new ShowPageCommand(Pages.ROOM_RESERVATION_SUCCESS);
            case "userReservationsPage":
                return new ShowUserReservationsPageCommand(serviceFactory);
            case "paymentPage":
                return new ShowPaymentPageCommand(serviceFactory);
            case "allReservationsPage":
                return new ShowAllReservationsRageCommand(serviceFactory);
            case "choosingRoomPage":
                return new ShowChoosingRoomPageCommand(serviceFactory);
            default:
                throw new IllegalArgumentException("Unknown type of Command " + type);
        }
    }
}
