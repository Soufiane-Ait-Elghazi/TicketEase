package org.hahn.TicketEase;

import org.hahn.TicketEase.entities.Ticket;
import org.hahn.TicketEase.entities.TicketCategory;
import org.hahn.TicketEase.entities.TicketComment;
import org.hahn.TicketEase.enums.TicketPriority;
import org.hahn.TicketEase.enums.TicketStatus;
import org.hahn.TicketEase.repositories.TicketCategoryRepository;
import org.hahn.TicketEase.repositories.TicketCommentRepository;
import org.hahn.TicketEase.repositories.TicketRepository;
import org.hahn.TicketEase.utils.BuiltInFunctions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class TicketEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketEaseApplication.class, args);
	}


	@Bean
	CommandLineRunner start(TicketCategoryRepository ticketCategoryRepository ,
							TicketRepository ticketRepository ,
							TicketCommentRepository  TicketCommentRepository
	){
		return args -> {
			Stream.of("Network","Hardware","Software").forEach(ticketCategoryName->{
				TicketCategory ticketCategory =new TicketCategory();
				ticketCategory.setName(ticketCategoryName);
				ticketCategory.setCreatedBy("SYSTEM");
				ticketCategory.setCreationDate(new Date().toInstant());
				ticketCategoryRepository.save(ticketCategory);
			});


			ticketCategoryRepository.findAll().forEach(ticketCategory -> {
				for (int i = 0 ;i<5; i++){
					Ticket ticket =  new Ticket();
					ticket.setKey("SIA-"+BuiltInFunctions.generateRandomSixDigit());
					ticket.setTitle(UUID.randomUUID().toString());
					ticket.setCategory(ticketCategory);
					ticket.setPriority(TicketPriority.HIGH);
					ticket.setStatus(TicketStatus.IN_PROGRESS);
					ticket.setCreatedBy("SYSTEM");
					ticket.setCreationDate(new Date().toInstant());
					ticketRepository.save(ticket);
				}
			});

			ticketRepository.findAll().forEach(ticket -> {
				for (int i = 0 ;i<5; i++){
					TicketComment comment =  new TicketComment();
					comment.setDescription(UUID.randomUUID().toString());
					comment.setTicket(ticket);
					comment.setCreatedBy("SYSTEM");
					comment.setCreationDate(new Date().toInstant());
					TicketCommentRepository.save(comment);
				}

			});



		};
	}

}
