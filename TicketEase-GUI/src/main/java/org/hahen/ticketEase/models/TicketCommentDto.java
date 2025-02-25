package org.hahen.ticketEase.models;


import lombok.Data;

@Data
public class TicketCommentDto {
        private Long id;
        private String description;
        private String creationDate;
        private String lastModifiedDate;
        private String createdBy;
        private String modifiedBy;

}
