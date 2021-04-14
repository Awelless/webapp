package com.epam.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieHandler {

    private static final int INFINITE_MAX_AGE = 60 * 60 * 24 * 365 * 20;

    public Optional<Cookie> getByName(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie);
            }
        }

        return Optional.empty();
    }

    public void setUnexpiring(HttpServletResponse response, String name, String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(INFINITE_MAX_AGE);
        response.addCookie(cookie);
    }
}
