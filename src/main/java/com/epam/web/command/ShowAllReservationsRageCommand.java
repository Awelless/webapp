package com.epam.web.command;

import com.epam.web.entity.RoomReservation;
import com.epam.web.pagination.Page;
import com.epam.web.pagination.PageRequest;
import com.epam.web.pagination.PageRequestExtractor;
import com.epam.web.pagination.PaginationUtils;
import com.epam.web.service.RoomReservationService;
import com.epam.web.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllReservationsRageCommand implements Command {

    private final RoomReservationService roomReservationService;
    private final PageRequestExtractor pageRequestExtractor;

    public ShowAllReservationsRageCommand(RoomReservationService roomReservationService, PageRequestExtractor pageRequestExtractor) {
        this.roomReservationService = roomReservationService;
        this.pageRequestExtractor = pageRequestExtractor;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        PageRequest pageRequest = pageRequestExtractor.extract(request);

        Page<RoomReservation> reservationsPage = roomReservationService.getAll(pageRequest);
        request.setAttribute("reservationsPage", reservationsPage);

        List<Integer> pageNumbers = PaginationUtils.createPageNumbersList(
                reservationsPage.getCurrentPage(), reservationsPage.getTotalPages());
        request.setAttribute("pageNumbers", pageNumbers);

        return CommandResult.forward(Pages.ALL_RESERVATIONS);
    }
}
