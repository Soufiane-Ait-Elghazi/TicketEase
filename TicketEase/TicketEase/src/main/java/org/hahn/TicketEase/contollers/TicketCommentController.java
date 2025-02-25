package org.hahn.TicketEase.contollers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.entities.TicketComment;
import org.hahn.TicketEase.services.ticketCommentService.TicketCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticketEase")
@CrossOrigin("*")
@AllArgsConstructor
@Slf4j
public class TicketCommentController {

    private TicketCommentService ticketCommentService;




    @PostMapping("/saveTicketComment/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Ticket Comment Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketComment.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "TicketEase APIs")
    public ResponseEntity<TicketComment> saveTicketComment(@PathVariable Long id , @RequestBody TicketComment ticketComment , Authentication authentication) {
        log.info("Saving Ticket Comment: {}", ticketComment);
        TicketComment savedTicketComment = ticketCommentService.saveTicketComment(id , ticketComment ,authentication);

        if (savedTicketComment != null) {
            log.info("Ticket Comment saved successfully with ID: {}", savedTicketComment.getId());
            return new ResponseEntity<>(savedTicketComment, HttpStatus.CREATED); // 201 Created status
        } else {
            log.error("Failed to save Ticket Comment: {}", ticketComment);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/tickets-comments")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tickets comments", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketComment.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Retrieve all ticket comments with pagination")
    public ResponseEntity<Page<TicketComment>> getAllTicketsComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketComment> comments = ticketCommentService.getAllTicketsComments(PageRequest.of(page, size));
        return ResponseEntity.ok(comments);
    }




    @GetMapping("/comments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket Comment", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketComment.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            })
    @Operation(summary = "Retrieve a ticket Comment by ID")
    public ResponseEntity<TicketComment> getCommentById(@PathVariable Long id) {
        return ticketCommentService.getTicketCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @PutMapping("/comments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated ticket Comment", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketComment.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            })
    @Operation(summary = "Update a ticket Comment by ID")
    public ResponseEntity<TicketComment> updateTicketComment(@PathVariable Long id, @RequestBody TicketComment updatedComment) {
        return ticketCommentService.updateTicketComment(id, updatedComment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted ticket Comment"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            })
    @Operation(summary = "Delete a ticket Comment by ID")
    public ResponseEntity<Void> deleteTicketComment(@PathVariable Long id) {
        if (ticketCommentService.deleteTicketComment(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }




    @GetMapping("/ticket-comments/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket comments", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketComment.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Retrieve all ticket comments with pagination")
    public ResponseEntity<List<TicketComment>> getTicketComments(
            @PathVariable Long id, Authentication authentication) {
        List<TicketComment> comments = ticketCommentService.getAllTicketComments(id,authentication);
        return ResponseEntity.ok(comments);
    }

}
