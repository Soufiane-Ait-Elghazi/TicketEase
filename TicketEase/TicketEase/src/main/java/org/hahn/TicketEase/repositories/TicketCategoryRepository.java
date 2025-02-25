package org.hahn.TicketEase.repositories;

import org.hahn.TicketEase.entities.TicketCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory,Long> {
    Page<TicketCategory> findAll(Pageable pageable);
    TicketCategory findByName(String name);
}
