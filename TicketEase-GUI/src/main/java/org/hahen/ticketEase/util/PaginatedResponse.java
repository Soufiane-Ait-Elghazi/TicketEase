package org.hahen.ticketEase.util;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private Pageable pageable;
    private boolean last;
    private int size;
    private int number;
    private boolean first;
    private int numberOfElements;
    private boolean empty;
    private Sort sort;

    // Pageable inner class
    @Data
    public static class Pageable {
        private int pageNumber;
        private int pageSize;
        private Sort sort;
        private int offset;
        private boolean paged;
        private boolean unpaged;
    }

    // Sort inner class
    @Data
    public static class Sort {
        private boolean empty;
        private boolean sorted;
        private boolean unsorted;
    }
}


