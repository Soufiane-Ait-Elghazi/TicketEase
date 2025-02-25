package org.hahn.TicketEase.services.ticketService;

import org.hahn.TicketEase.dtos.ChangeTicketStatusDto;
import org.hahn.TicketEase.dtos.SearchTicketFormDto;
import org.hahn.TicketEase.dtos.TicketDto;
import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface TicketService {


    Ticket saveTicket(TicketDto ticket , Authentication authentication);
    Ticket getTicketById(Long id , Authentication authentication);
    Page<Ticket> getAllTickets(Pageable pageable , Authentication authentication);
    Optional<Ticket> updateTicket(Long id, Ticket ticket);
    boolean deleteTicket(Long id);
    Page<Ticket> getTicketWithFilter(Pageable pageable, SearchTicketFormDto searchTicketFormDto, Authentication authentication);

    Ticket changeTicketStatus(ChangeTicketStatusDto changeTicketStatusDto, Authentication authentication);
}
