package org.hahn.TicketEase.dtos;

import lombok.Data;

@Data
public class SearchTicketFormDto {
    private String key;
    private String title;
    private String priority;
    private String category;
    private String status;
}
