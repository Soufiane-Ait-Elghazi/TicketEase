package org.hahen.ticketEase.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.enums.HttpMethods;
import org.hahen.ticketEase.models.SearchTicketFormDto;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.network.Http;
import org.hahen.ticketEase.util.PaginatedResponse;

import java.net.http.HttpResponse;

public class TicketService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static PaginatedResponse<TicketDto> getTickets(int page, int size) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.GET,
                GlobalVariables.BASE_API_URL + "/tickets?page=" + page + "&size=" + size,
                null,
                null
        );
        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch tickets. HTTP Status: " + response.statusCode());
        }
        PaginatedResponse<TicketDto> paginatedResponse = objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(PaginatedResponse.class, TicketDto.class));
        return paginatedResponse;
    }



    public static TicketDto getTicketById(Long id) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.GET,
                GlobalVariables.BASE_API_URL + "/ticket/"+id,
                null,
                null
        );
        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch tickets. HTTP Status: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(),
                 objectMapper.getTypeFactory().constructType(TicketDto.class));
    }



     public static PaginatedResponse<TicketDto> getTicketsWithFilters(int page, int size, SearchTicketFormDto searchTicketFormDto) throws Exception {
             HttpResponse<String> response = Http.sendRequest(
                     HttpMethods.POST,
                     GlobalVariables.BASE_API_URL + "/tickets-with-filter?page=" + page + "&size=" + size,
                     null,
                      searchTicketFormDto
             );
             if (response.statusCode() != 200) {
                 throw new Exception("Failed to fetch tickets. HTTP Status: " + response.statusCode());
             }
         return objectMapper.readValue(response.body(),
                 objectMapper.getTypeFactory().constructParametricType(PaginatedResponse.class, TicketDto.class));

    }


    public static boolean deleteTicketById(Long id) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.DELETE,
                GlobalVariables.BASE_API_URL + "/ticket/"+id,
                null,
                null
        );
        return response.statusCode() == 200;
    }

    public static TicketDto newTicket(TicketDto newTicketDto) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.POST,
                GlobalVariables.BASE_API_URL + "/saveTicket",
                null,
                newTicketDto
        );
        if (response.statusCode() != 201) {
            throw new Exception("Failed to fetch tickets. HTTP Status: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructType(TicketDto.class));
    }
}
