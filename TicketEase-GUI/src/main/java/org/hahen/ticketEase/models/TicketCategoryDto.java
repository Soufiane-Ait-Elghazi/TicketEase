package org.hahen.ticketEase.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TicketCategoryDto {
    private Long id;
    private String name;
    private String creationDate;
    private String lastModifiedDate;
    private String createdBy;
    private String modifiedBy;
}
