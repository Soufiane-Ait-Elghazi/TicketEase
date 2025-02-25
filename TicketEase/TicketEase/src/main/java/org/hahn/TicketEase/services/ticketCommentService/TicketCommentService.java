package org.hahn.TicketEase.services.ticketCommentService;

import org.hahn.TicketEase.entities.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface TicketCommentService {

    TicketComment saveTicketComment(Long ticketId , TicketComment ticketComment , Authentication authentication);
    Optional<TicketComment> getTicketCommentById(Long id);
    Page<TicketComment> getAllTicketsComments(Pageable pageable);
    Optional<TicketComment> updateTicketComment(Long id, TicketComment ticket);
    boolean deleteTicketComment(Long id);
    Optional<TicketComment> getTicketDescription(String name);
    List<TicketComment> getAllTicketComments(Long idTicket , Authentication authentication);
}
