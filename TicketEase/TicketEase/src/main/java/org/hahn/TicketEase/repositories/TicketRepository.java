package org.hahn.TicketEase.repositories;

import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.enums.TicketPriority;
import org.hahn.TicketEase.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Page<Ticket> findAll(Pageable pageable);
    Page<Ticket> findByCreatedBy(String name, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category")
    Page<Ticket> findByTitleISFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.priority =:priority")
    Page<Ticket> findByTitleISpFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("priority") TicketPriority priority,
            Pageable pageable);
    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.status =:status")
    Page<Ticket> findByTitleISsFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("status") TicketStatus status,
            Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.priority =:priority and t.status =:status")
    Page<Ticket> findByTitleISFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("priority") TicketPriority priority,
            @Param("status") TicketStatus status,
            Pageable pageable);




    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.key like :key and t.category.name like :category and t.createdBy like :createdBy")
    Page<Ticket> findByTitleNSFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("createdBy") String createdUser,
            Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.priority =:priority and t.createdBy like :createdBy")
    Page<Ticket> findByTitleNSpFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("priority") String priority,
            @Param("createdBy") String createdUser,
            Pageable pageable);
    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.status =:status and t.createdBy like :createdBy")
    Page<Ticket> findByTitleNSsFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("status") String status,
            @Param("createdBy") String createdUser,
            Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.title LIKE :title and t.key like :key and t.category.name like :category and t.priority =:priority and t.status =:status and t.createdBy like :createdBy")
    Page<Ticket> findByTitleNSFilter(
            @Param("title") String title,
            @Param("key") String key,
            @Param("category") String category,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("createdBy") String createdUser,
            Pageable pageable);


















}
