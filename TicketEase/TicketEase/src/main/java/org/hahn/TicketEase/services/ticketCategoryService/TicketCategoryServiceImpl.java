package org.hahn.TicketEase.services.ticketCategoryService;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.repositories.TicketCategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TicketCategoryServiceImpl implements TicketCategoryService {

    private  TicketCategoryRepository ticketCategoryRepository;

    @Override
    public TicketCategory saveTicketCategory(TicketCategory ticketCategory, Authentication authentication) {
        log.info("Saving ticket category: {}", ticketCategory.getName());
        ticketCategory.setId(null);
        ticketCategory.setCreationDate(new Date().toInstant());
        ticketCategory.setModifiedBy(null);
        ticketCategory.setLastModifiedDate(null);
        ticketCategory.setCreatedBy(authentication.getName());
        return ticketCategoryRepository.save(ticketCategory);
    }

    @Override
    public Optional<TicketCategory> getTicketCategoryById(Long id) {
        log.info("Fetching ticket category by ID: {}", id);
        return ticketCategoryRepository.findById(id);
    }


    @Override
    public Page<TicketCategory> getAllTicketCategories(Pageable pageable) {
        log.info("Fetching all ticket categories");
        return  ticketCategoryRepository.findAll(pageable);
    }

    @Override
    public Optional<TicketCategory> updateTicketCategory(Long id, TicketCategory ticketCategory) {
        log.info("Updating ticket category with ID: {}", id);
        return ticketCategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(ticketCategory.getName());
                    existingCategory.setId(id);
                    existingCategory.setLastModifiedDate(new Date().toInstant());
                    return ticketCategoryRepository.save(existingCategory);
                });
    }


    @Override
    public boolean deleteTicketCategory(Long id) {
        log.info("Deleting ticket category with ID: {}", id);
        ticketCategoryRepository.findById(id)
                .ifPresentOrElse(
                        ticketCategoryRepository::delete,
                        () -> { throw new EntityNotFoundException("TicketCategory not found with ID: " + id); }
                );
        return false;
    }

    @Override
    public TicketCategory getTicketCategoryByName(String name) {
        log.info("Fetching ticket category by name: {}", name);
        return ticketCategoryRepository.findByName(name);
    }
}
