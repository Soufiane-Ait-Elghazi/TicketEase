package org.hahn.TicketEase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

