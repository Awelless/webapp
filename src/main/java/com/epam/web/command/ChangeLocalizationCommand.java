package com.epam.web.command;

import com.epam.web.localization.Localization;
import com.epam.web.service.ServiceException;
import com.epam.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocalizationCommand implements Command {

    private static final String LOCALIZATION_COOKIE_NAME = "localization";

    private final CookieHandler cookieHandler = new CookieHandler();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String localizationParam = request.getParameter("value");
        Localization localization = Localization.valueOf(localizationParam);

        Cookie cookie = new Cookie(LOCALIZATION_COOKIE_NAME, localization.name());
        cookieHandler.set(response, cookie);

        String previousParams = getPreviousRequestParams(request);

        return CommandResult.redirect(request.getRequestURI() + '?' + previousParams);
    }

    private String getPreviousRequestParams(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("previousParams");
    }
}
