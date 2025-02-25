package org.hahen.ticketEase.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.enums.HttpMethods;
import org.hahen.ticketEase.models.TicketCategoryDto;
import org.hahen.ticketEase.models.TicketCommentDto;
import org.hahen.ticketEase.models.TicketDto;
import org.hahen.ticketEase.network.Http;
import org.hahen.ticketEase.util.PaginatedResponse;

import java.net.http.HttpResponse;
import java.util.List;


public class TicketCommentService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<TicketCommentDto> getTicketComments(Long ticketId) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.GET,
                GlobalVariables.BASE_API_URL + "/ticket-comments/" + ticketId,
                null,
                null
        );
        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch ticket comments. HTTP Status: " + response.statusCode());
        }
        return objectMapper.readValue(
                response.body(),
                new TypeReference<List<TicketCommentDto>>() {}
        );
    }

    public static TicketCommentDto addNewComment(Long ticketId , TicketCommentDto ticketCommentDto) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.POST,
                GlobalVariables.BASE_API_URL + "/saveTicketComment/" + ticketId,
                null,
                ticketCommentDto
        );
        if (response.statusCode() != 201) {
            throw new Exception("Failed to fetch ticket comments. HTTP Status: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructType(TicketCommentDto.class));
    }
}
