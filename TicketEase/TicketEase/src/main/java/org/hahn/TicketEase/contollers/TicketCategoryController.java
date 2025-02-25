package org.hahn.TicketEase.contollers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.services.ticketCategoryService.TicketCategoryService;
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
public class TicketCategoryController {

    private  TicketCategoryService ticketCategoryService;



    @PostMapping("/saveTicketCategory")
    @PreAuthorize("hasAnyAuthority('SCOPE_IT_SUPPORT')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Ticket Category Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Springdoc OpenAPI sample API")
    public ResponseEntity<TicketCategory> saveTicketCategory(@RequestBody TicketCategory ticketCategory , Authentication authentication) {
        log.info("Saving Ticket Category: {}", ticketCategory);
        TicketCategory savedTicketCategory = ticketCategoryService.saveTicketCategory(ticketCategory ,authentication);

        if (savedTicketCategory != null) {
            log.info("Ticket Category saved successfully with ID: {}", savedTicketCategory.getId());
            return new ResponseEntity<>(savedTicketCategory, HttpStatus.CREATED); // 201 Created status
        } else {
            log.error("Failed to save Ticket Category: {}", ticketCategory);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/ticket-categories")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket categories", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
            })
    @Operation(summary = "Retrieve all ticket categories with pagination")
    public ResponseEntity<Page<TicketCategory>> getTicketCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketCategory> categories = ticketCategoryService.getAllTicketCategories(PageRequest.of(page, size));
        return ResponseEntity.ok(categories);
    }




    @GetMapping("/ticket-categories/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_EMPLOYEE')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket category", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
            })
    @Operation(summary = "Retrieve a ticket category by ID")
    public ResponseEntity<TicketCategory> getTicketCategoryById(@PathVariable Long id) {
        return ticketCategoryService.getTicketCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @PutMapping("/ticket-categories/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_IT_SUPPORT')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated ticket category", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCategory.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Unauthorized invalid authentication rights", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
            })
    @Operation(summary = "Update a ticket category by ID")
    public ResponseEntity<TicketCategory> updateTicketCategory(@PathVariable Long id, @RequestBody TicketCategory updatedCategory) {
        return ticketCategoryService.updateTicketCategory(id, updatedCategory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @DeleteMapping("/ticket-categories/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_IT_SUPPORT')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted ticket category"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
            })
    @Operation(summary = "Delete a ticket category by ID")
    public ResponseEntity<Void> deleteTicketCategory(@PathVariable Long id) {
        if (ticketCategoryService.deleteTicketCategory(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }





}

