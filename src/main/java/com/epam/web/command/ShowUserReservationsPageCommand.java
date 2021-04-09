package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.entity.User;
import com.epam.web.pagination.Page;
import com.epam.web.pagination.PageRequest;
import com.epam.web.pagination.PageRequestExtractor;
import com.epam.web.pagination.PaginationUtils;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserReservationsPageCommand implements Command {

    private final RoomReservationService roomReservationService;
    private final PageRequestExtractor pageRequestExtractor;

    public ShowUserReservationsPageCommand(RoomReservationService roomReservationService, PageRequestExtractor pageRequestExtractor) {
        this.roomReservationService = roomReservationService;
        this.pageRequestExtractor = pageRequestExtractor;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        User user = (User) request.getSession().getAttribute("user");

        PageRequest pageRequest = pageRequestExtractor.extract(request);

        Page<RoomReservation> reservationsPage = roomReservationService.getByUser(user, pageRequest);
        request.setAttribute("reservationsPage", reservationsPage);

        List<Integer> pageNumbers = PaginationUtils.createPageNumbersList(
                reservationsPage.getCurrentPage(), reservationsPage.getTotalPages());
        request.setAttribute("pageNumbers", pageNumbers);

        return CommandResult.forward(Pages.USER_RESERVATIONS);
    }
}
