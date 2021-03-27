package com.epam.web.command;

import com.epam.web.localization.Localization;
import com.epam.web.service.ServiceException;
import com.epam.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ChangeLocalizationCommand implements Command {

    private static final String LOCALIZATION_COOKIE_NAME = "localization";
    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";

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

        Optional<Cookie> previousParamsOptional = cookieHandler.getByName(request, PREVIOUS_PARAMS_COOKIE_NAME);
        return previousParamsOptional.map(Cookie::getValue).orElse(null);
    }
}
