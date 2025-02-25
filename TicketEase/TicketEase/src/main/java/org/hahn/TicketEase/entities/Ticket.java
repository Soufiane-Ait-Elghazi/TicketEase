package org.hahn.TicketEase.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hahn.TicketEase.enums.TicketPriority;
import org.hahn.TicketEase.enums.TicketStatus;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class Ticket extends AbstractEntity {

    @Column(name = "ticket_key", nullable = false ,unique = true)
    private String key;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JsonIgnore
    private TicketCategory category;



    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TicketComment> comments;
}

