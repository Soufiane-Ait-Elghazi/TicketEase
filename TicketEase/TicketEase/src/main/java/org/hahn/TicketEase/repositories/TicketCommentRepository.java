package org.hahn.TicketEase.repositories;

import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.entities.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketCommentRepository extends JpaRepository<TicketComment,Long> {
    Page<TicketComment> findAll(Pageable pageable);
    Optional<TicketComment> findByDescription(String name);
    List<TicketComment> findByTicket(Ticket ticket);
}
