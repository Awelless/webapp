package com.epam.web.pagination;

import java.util.List;

public class Page<T> {

    private final List<T> content;
    private final int totalPages;
    private final int currentPage;

    public Page(List<T> content, int totalPages, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
