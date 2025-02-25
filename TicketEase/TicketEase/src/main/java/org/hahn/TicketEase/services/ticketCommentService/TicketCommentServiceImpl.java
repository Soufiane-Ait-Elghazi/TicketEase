package org.hahn.TicketEase.services.ticketCommentService;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketComment;
import org.hahn.TicketEase.repositories.TicketCommentRepository;
import org.hahn.TicketEase.services.ticketService.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
@AllArgsConstructor
public class TicketCommentServiceImpl implements TicketCommentService{
    private TicketCommentRepository ticketCommentRepository ;
    private TicketService ticketService ;


    @Override
    public TicketComment saveTicketComment(Long ticketId, TicketComment ticketComment, Authentication authentication) {
        Ticket ticket = ticketService.getTicketById(ticketId, authentication);
        if (ticket == null) {
            log.error("Ticket with ID " + ticketId + " not found.");
        }
        ticketComment.setId(null);
        ticketComment.setModifiedBy(null);
        ticketComment.setLastModifiedDate(null);
        ticketComment.setCreatedBy(authentication.getName());
        ticketComment.setCreationDate(new Date().toInstant());
        ticketComment.setTicket(ticket);
        return ticketCommentRepository.save(ticketComment);
    }

    @Override
    public Optional<TicketComment> getTicketCommentById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<TicketComment> getAllTicketsComments(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<TicketComment> updateTicketComment(Long id, TicketComment ticket) {
        return Optional.empty();
    }

    @Override
    public boolean deleteTicketComment(Long id) {
        return false;
    }

    @Override
    public Optional<TicketComment> getTicketDescription(String name) {
        return Optional.empty();
    }

    @Override
    public List<TicketComment> getAllTicketComments(Long idTicket, Authentication authentication) {
        Ticket ticket = ticketService.getTicketById(idTicket,authentication);
        return ticketCommentRepository.findByTicket(ticket);

    }
}
