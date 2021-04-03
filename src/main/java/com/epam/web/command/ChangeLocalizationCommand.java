package com.epam.web.command;

import com.epam.web.localization.Localization;
import com.epam.web.service.ServiceException;
import com.epam.web.util.CookieHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ChangeLocalizationCommand implements Command {

    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";
    private static final String LOCALIZATION_COOKIE_NAME = "localization";

    private final CookieHandler cookieHandler = new CookieHandler();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String localizationParam = request.getParameter("value");
        Localization localization = Localization.valueOf(localizationParam);

        cookieHandler.setUnexpiring(response, LOCALIZATION_COOKIE_NAME, localization.name());

        Optional<Cookie> optionalPreviousParamsCookie = cookieHandler.getByName(request, PREVIOUS_PARAMS_COOKIE_NAME);

        if (optionalPreviousParamsCookie.isPresent()) {

            String previousParams = optionalPreviousParamsCookie.get().getValue();

            if (previousParams != null && !previousParams.trim().isEmpty()) {
                previousParams = '?' + previousParams;
            }

            return CommandResult.redirect(request.getRequestURI() + previousParams);
        }

        return CommandResult.redirect(request.getRequestURI());
    }
}
