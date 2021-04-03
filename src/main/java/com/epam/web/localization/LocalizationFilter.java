package com.epam.web.localization;

import com.epam.web.util.CookieHandler;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class LocalizationFilter implements Filter {

    private static final String LOCALIZATION_COOKIE_NAME = "localization";

    private CookieHandler cookieHandler;

    @Override
    public void init(FilterConfig filterConfig) {
        cookieHandler = new CookieHandler();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            Optional<Cookie> optionalCookie = cookieHandler.getByName(httpServletRequest, LOCALIZATION_COOKIE_NAME);

            if (optionalCookie.isPresent()) {

                String cookieValue = optionalCookie.get().getValue();

                if (!Localization.isValid(cookieValue)) {
                    cookieValue = null;
                }

                httpServletRequest.setAttribute("localization", cookieValue);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        cookieHandler = null;
    }
}
