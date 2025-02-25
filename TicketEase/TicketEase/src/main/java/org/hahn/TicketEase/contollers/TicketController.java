package org.hahn.TicketEase.contollers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.dtos.ChangeTicketStatusDto;
import org.hahn.TicketEase.dtos.SearchTicketFormDto;
import org.hahn.TicketEase.dtos.TicketDto;
import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.services.ticketService.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketEase")
@CrossOrigin("*")
@AllArgsConstructor
@Slf4j
public class TicketController {



    private TicketService ticketService;




    @GetMapping("/tickets")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tickets", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Retrieve all tickets with pagination")
    public ResponseEntity<Page<Ticket>> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        Page<Ticket> tickets = ticketService.getAllTickets(PageRequest.of(page, size) , authentication);
        return ResponseEntity.ok(tickets);
    }




    @GetMapping("/ticket/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content)
            })
    @Operation(summary = "Retrieve a ticket by ID")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id,
                                                Authentication authentication) {

          Ticket ticket = ticketService.getTicketById(id , authentication);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/saveTicket")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Ticket Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Springdoc OpenAPI sample API")
    public ResponseEntity<Ticket> saveTicket(@RequestBody TicketDto ticket , Authentication authentication) {
        log.info("Saving Ticket : {}", ticket);
        Ticket savedTicket = ticketService.saveTicket(ticket ,authentication);

        if (savedTicket != null) {
            log.info("Ticket  saved successfully with ID: {}", savedTicket.getId());
            return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
        } else {
            log.error("Failed to save Ticket : {}", ticket);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTicket/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Ticket Updated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Springdoc OpenAPI sample API")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id , @RequestBody TicketDto ticket , Authentication authentication) {
        log.info("Update Ticket : {}", ticket);
        Ticket savedTicket = ticketService.updateTicket(id,ticket ,authentication);

        if (savedTicket != null) {
            log.info("Ticket updated successfully with ID: {}", savedTicket.getId());
            return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
        } else {
            log.error("Failed to update Ticket : {}", ticket);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @PostMapping("/tickets-with-filter")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tickets", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content)
            })
    @Operation(summary = "Retrieve tickets by Title")
    public ResponseEntity<Page<Ticket>> getTicketWithFilter(@RequestBody SearchTicketFormDto ticketSearchDto,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      Authentication authentication) {
        Page<Ticket> tickets = ticketService.getTicketWithFilter(PageRequest.of(page, size),ticketSearchDto, authentication);
        return ResponseEntity.ok(tickets);
    }




    @PostMapping("/change-ticket-status")
    @PreAuthorize("hasAnyAuthority('SCOPE_IT_SUPPORT')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully changed the status", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Change the status of a ticket")
    public ResponseEntity<Ticket> changeTicketStatus(@RequestBody ChangeTicketStatusDto changeTicketStatusDto ,Authentication authentication ) {
        Ticket ticket = ticketService.changeTicketStatus(changeTicketStatusDto, authentication);
        return ResponseEntity.ok(ticket);
    }


    @DeleteMapping("/ticket/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_IT_SUPPORT')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted ticket "),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "ticket not found", content = @Content)
            })
    @Operation(summary = "Delete a ticket  by ID")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (ticketService.deleteTicket(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }






}
