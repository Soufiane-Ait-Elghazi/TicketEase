package org.hahn.TicketEase.services.ticketCategoryService;

import org.hahn.TicketEase.entities.TicketCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface TicketCategoryService {
    TicketCategory saveTicketCategory(TicketCategory ticketCategory, Authentication authentication);
    Optional<TicketCategory> getTicketCategoryById(Long id);
    Page<TicketCategory> getAllTicketCategories(Pageable pageable);
    Optional<TicketCategory> updateTicketCategory(Long id, TicketCategory ticketCategory);
    boolean deleteTicketCategory(Long id);
    TicketCategory getTicketCategoryByName(String name);
}
