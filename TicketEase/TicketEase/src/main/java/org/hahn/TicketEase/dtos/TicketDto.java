package org.hahn.TicketEase.dtos;

import lombok.Data;

@Data
public class TicketDto {
    private Long id;
    private String key;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String category;
    private String creationDate;
    private String lastModifiedDate;
    private String createdBy;
    private String modifiedBy;
}

