package com.epam.web.command;

import com.epam.web.service.ServiceFactory;

public class CommandFactory {

    private final ServiceFactory serviceFactory = new ServiceFactory();

    public Command create(String type) {

        if (type == null) {
            return new ShowPageCommand(Pages.LOGIN);
        }

        switch (type) {
            case Commands.LOGIN:
                return new LoginCommand(serviceFactory.createUserService());
            case Commands.LOGOUT:
                return new LogoutCommand();
            case Commands.REGISTRATION:
                return new RegistrationCommand(serviceFactory.createUserService());
            case Commands.CHANGE_LOCALIZATION:
                return new ChangeLocalizationCommand();
            case Commands.RESERVE_ROOM:
                return new ReserveRoomCommand(serviceFactory.createRoomReservationService());
            case Commands.RESERVATION_APPROVE:
                return new ReservationApproveCommand(serviceFactory.createRoomReservationService(),
                                                     serviceFactory.createRoomService());
            case Commands.RESERVATION_REJECT:
                return new ReservationRejectCommand(serviceFactory.createRoomReservationService());
            case Commands.RESERVATION_PAY:
                return new ReservationPayCommand(serviceFactory.createRoomReservationService());
            case Commands.RESERVATION_CANCEL:
                return new ReservationCancelCommand(serviceFactory.createRoomReservationService());
            case Commands.LOGIN_PAGE:
                return new ShowPageCommand(Pages.LOGIN);
            case Commands.REGISTRATION_PAGE:
                return new ShowPageCommand(Pages.REGISTRATION);
            case Commands.NEW_RESERVATION_PAGE:
                return new ShowPageCommand(Pages.ROOM_RESERVATION);
            case Commands.NEW_RESERVATION_SUCCESS_PAGE:
                return new ShowPageCommand(Pages.ROOM_RESERVATION_SUCCESS);
            case Commands.USER_RESERVATIONS_PAGE:
                return new ShowUserReservationsPageCommand(serviceFactory.createRoomReservationService());
            case Commands.PAYMENT_PAGE:
                return new ShowPaymentPageCommand(serviceFactory.createRoomReservationService());
            case Commands.ALL_RESERVATIONS_PAGE:
                return new ShowAllReservationsRageCommand(serviceFactory.createRoomReservationService());
            case Commands.CHOOSING_ROOM_PAGE:
                return new ShowChoosingRoomPageCommand(serviceFactory.createRoomReservationService(),
                                                       serviceFactory.createRoomService());
            default:
                throw new IllegalArgumentException("Unknown type of Command " + type);
        }
    }
}
