package org.hahn.TicketEase.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class TicketComment extends AbstractEntity {

    private String description;

    @ManyToOne
    @JsonIgnore
    private Ticket ticket;
}
