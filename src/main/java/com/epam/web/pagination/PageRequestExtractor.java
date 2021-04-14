package com.epam.web.pagination;

import javax.servlet.http.HttpServletRequest;

public class PageRequestExtractor {

    public static final int PAGE_SIZE = 10;

    public PageRequest extract(HttpServletRequest request) {

        String pageValue = request.getParameter("page");
        String sizeValue = request.getParameter("size");

        int pageNumber = 1;
        try {
            pageNumber = Integer.parseInt(pageValue);
        } catch (NumberFormatException ignored) {
            //if values are invalid, default values applied ... ignoring
        }

        return new PageRequest(pageNumber, PAGE_SIZE);
    }
}
