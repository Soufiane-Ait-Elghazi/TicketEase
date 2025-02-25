package org.hahn.TicketEase.mappers;

import org.hahn.TicketEase.dtos.TicketDto;
import org.hahn.TicketEase.entities.Ticket;

public class TicketMapper {

    public static TicketDto convertToDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setKey(ticket.getKey());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(String.valueOf(ticket.getPriority()));
        dto.setStatus(String.valueOf(ticket.getStatus()));
        dto.setCategory(String.valueOf(ticket.getCategory().getName()));
        dto.setCreationDate(String.valueOf(ticket.getCreationDate()));
        dto.setLastModifiedDate(String.valueOf(ticket.getLastModifiedDate()));
        dto.setCreatedBy(ticket.getCreatedBy());
        dto.setModifiedBy(ticket.getModifiedBy());
        return dto;
    }

}
