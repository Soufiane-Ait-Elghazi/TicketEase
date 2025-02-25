package org.hahn.TicketEase.services.ticketService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.dtos.ChangeTicketStatusDto;
import org.hahn.TicketEase.dtos.SearchTicketFormDto;
import org.hahn.TicketEase.dtos.TicketDto;
import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.enums.TicketPriority;
import org.hahn.TicketEase.enums.TicketStatus;
import org.hahn.TicketEase.repositories.TicketRepository;
import org.hahn.TicketEase.services.ticketCategoryService.TicketCategoryService;
import org.hahn.TicketEase.utils.BuiltInFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{
    private TicketCategoryService ticketCategoryService;
    private  TicketRepository ticketRepository;
    @Override
    public Ticket saveTicket(TicketDto ticketDto , Authentication authentication) {
        Ticket ticket = new Ticket();
        TicketCategory ticketCategory = ticketCategoryService.getTicketCategoryByName(String.valueOf(ticketDto.getCategory()));
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setId(null);
        ticket.setComments(null);
        ticket.setCreatedBy(authentication.getName());
        ticket.setCreationDate(new Date().toInstant());
        ticket.setKey("SIA"+ BuiltInFunctions.generateRandomSixDigit());
        ticket.setStatus(TicketStatus.NEW);
        ticket.setCategory(ticketCategory);
        ticket.setPriority(TicketPriority.valueOf(ticketDto.getPriority()));
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id, Authentication authentication) {
        log.info("Fetching ticket by ID: {}", id);
        boolean hasItSupportRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_IT_SUPPORT"));
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            Ticket t = ticket.get();
            if (hasItSupportRole) {
                return t;
            }
            if (t.getCreatedBy().equals(authentication.getName())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable, Authentication authentication) {
        boolean hasItSupportRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_IT_SUPPORT"));

        Page<Ticket> tickets = hasItSupportRole
                ? ticketRepository.findAll(pageable)
                : ticketRepository.findByCreatedBy(authentication.getName(), pageable);

        return tickets;
    }

    @Override
    public Ticket updateTicket(Long id, TicketDto ticketDto, Authentication authentication) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(null);

        TicketCategory ticketCategory = ticketCategoryService.getTicketCategoryByName(String.valueOf(ticketDto.getCategory()));
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setModifiedBy(authentication.getName());
        ticket.setLastModifiedDate(new Date().toInstant());
        ticket.setPriority(TicketPriority.valueOf(ticketDto.getPriority()));
        ticket.setStatus(TicketStatus.valueOf(ticketDto.getStatus()));
        ticket.setCategory(ticketCategory);
        return ticketRepository.save(ticket);
    }


    @Override
    public boolean deleteTicket(Long id) {
        log.info("Deleting ticket with ID: {}", id);
        ticketRepository.findById(id)
                .ifPresentOrElse(
                        ticketRepository::delete,
                        () -> { throw new EntityNotFoundException("Ticket not found with ID: " + id); }
                );
        return false;
    }


    @Override
    public Page<Ticket> getTicketWithFilter(Pageable pageable, SearchTicketFormDto searchTicketFormDto, Authentication authentication) {

        String priority = null;
        String status = null;

        String key = (searchTicketFormDto.getKey() != null && !searchTicketFormDto.getKey().isEmpty())
                ? "%"+searchTicketFormDto.getKey()+"%"
                : "%%";

        String title = (searchTicketFormDto.getTitle() != null && !searchTicketFormDto.getTitle().isEmpty())
                ? "%"+searchTicketFormDto.getTitle()+"%"
                : "%%";

        String category = (searchTicketFormDto.getCategory() != null && !searchTicketFormDto.getCategory().isEmpty())
                ? "%"+searchTicketFormDto.getCategory()+"%"
                : "%%";

        if(searchTicketFormDto.getPriority() != null && TicketPriority.contains(searchTicketFormDto.getPriority())){
            for (TicketPriority ticketPriority : TicketPriority.values()) {
                if (ticketPriority.getText().equalsIgnoreCase(searchTicketFormDto.getPriority())) {
                    priority = String.valueOf(ticketPriority);
                    break;
                }
            }
        }

        if(searchTicketFormDto.getStatus() != null && TicketStatus.contains(searchTicketFormDto.getStatus())){
            for (TicketStatus ticketStatus : TicketStatus.values()) {
                if (ticketStatus.getText().equalsIgnoreCase(searchTicketFormDto.getStatus())) {
                    status = String.valueOf(ticketStatus);
                    break;
                }
            }
        }

        boolean hasItSupportRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_IT_SUPPORT"));
        if (hasItSupportRole) {

            if(status == null ){
                if(priority == null){
                    return ticketRepository.findByTitleISFilter(title,key,category, pageable);
                }else{
                    return ticketRepository.findByTitleISpFilter(title,key,category, TicketPriority.valueOf(priority), pageable);
                }
            }else{
                if(priority == null){
                    return ticketRepository.findByTitleISsFilter(title,key,category, TicketStatus.valueOf(status), pageable);
                }else{
                    return ticketRepository.findByTitleISFilter(title,key,category,TicketPriority.valueOf(priority),TicketStatus.valueOf(status), pageable);
                }
            }

        } else {

            if(status == null){
                if(priority == null){
                    return ticketRepository.findByTitleNSFilter(title,key,category,authentication.getName(), pageable);
                }else{
                    return ticketRepository.findByTitleNSpFilter(title,key,category,priority,authentication.getName(), pageable);
                }
            }else{
                if(priority == null){
                    return ticketRepository.findByTitleNSsFilter(title,key,category,status,authentication.getName(),pageable);
                }else{
                    return ticketRepository.findByTitleNSFilter(title,key,category,priority,status,authentication.getName(),pageable);
                }
            }
        }
    }

    @Override
    public Ticket changeTicketStatus(ChangeTicketStatusDto changeTicketStatusDto,Authentication authentication) {
        Ticket ticket = ticketRepository.findById(changeTicketStatusDto.getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setModifiedBy(authentication.getName());
        ticket.setLastModifiedDate(new Date().toInstant());
        ticket.setStatus(changeTicketStatusDto.getNewStatus());
        return ticketRepository.save(ticket);
    }
}
