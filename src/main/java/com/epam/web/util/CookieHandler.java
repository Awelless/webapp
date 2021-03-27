package com.epam.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieHandler {

    public Optional<Cookie> getByName(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie);
            }
        }

        return Optional.empty();
    }

    public void set(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }
}
