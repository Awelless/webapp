package com.epam.web.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PaginationUtils {

    private static final int MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER = 9;

    private PaginationUtils() {
    }

    public static List<Integer> createPageNumbersList(int currentPage, int totalPages) {

        if (totalPages <= MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER) {
            return IntStream.range(1, totalPages + 1)
                    .boxed()
                    .collect(Collectors.toList());
        }

        if (currentPage <= MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER / 2 + 1) {

            List<Integer> numbers = IntStream.range(1, MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER - 1)
                    .boxed()
                    .collect(Collectors.toList());

            numbers.add(null);
            numbers.add(totalPages);

            return numbers;
        }

        if (currentPage >= totalPages - MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER / 2) {

            List<Integer> numbers = new ArrayList<>();
            numbers.add(1);
            numbers.add(null);

            int startFrom = totalPages - MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER / 2 - 2;

            List<Integer> additionalNumbers = IntStream.range(startFrom, totalPages + 1)
                    .boxed()
                    .collect(Collectors.toList());

            numbers.addAll(additionalNumbers);

            return numbers;
        }

        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(null);

        int delta = (MAX_PAGE_NAVIGATION_ELEMENTS_NUMBER - 4) / 2;

        List<Integer> additionalNumbers = IntStream.range(currentPage - delta, currentPage + delta + 1)
                .boxed()
                .collect(Collectors.toList());

        numbers.addAll(additionalNumbers);

        numbers.add(null);
        numbers.add(totalPages);

        return numbers;
    }
}
