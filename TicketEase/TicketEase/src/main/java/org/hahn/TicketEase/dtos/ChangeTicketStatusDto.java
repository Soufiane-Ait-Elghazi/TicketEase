package org.hahn.TicketEase.dtos;

import lombok.Data;
import org.hahn.TicketEase.enums.TicketStatus;


@Data
public class ChangeTicketStatusDto {
    private Long id;
    private TicketStatus oldStatus;
    private TicketStatus newStatus;
}
