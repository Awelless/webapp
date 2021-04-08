package com.epam.web.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveParamsFilter implements Filter {

    private static final String PREVIOUS_PARAMS_COOKIE_NAME = "previousParams";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            if ("GET".equals(httpServletRequest.getMethod())) {
                String query = httpServletRequest.getQueryString();
                CookieHandler cookieHandler = new CookieHandler();
                cookieHandler.setUnexpiring(httpServletResponse, PREVIOUS_PARAMS_COOKIE_NAME, query);
            }
        }

        filterChain.doFilter(request, response);
    }
}
