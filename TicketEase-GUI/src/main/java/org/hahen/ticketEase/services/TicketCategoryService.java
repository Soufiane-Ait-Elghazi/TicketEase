package org.hahen.ticketEase.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.enums.HttpMethods;
import org.hahen.ticketEase.models.TicketCategoryDto;
import org.hahen.ticketEase.network.Http;
import org.hahen.ticketEase.util.PaginatedResponse;

import java.net.http.HttpResponse;
import java.util.List;


public class TicketCategoryService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static PaginatedResponse<TicketCategoryDto>  getTicketCategories(int page, int size) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.GET,
                GlobalVariables.BASE_API_URL + "/ticket-categories?page=" + page + "&size=" + size,
                null,
                null
        );
        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch ticket categories. HTTP Status: " + response.statusCode());
        }
        PaginatedResponse<TicketCategoryDto> paginatedResponse = objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructParametricType(PaginatedResponse.class, TicketCategoryDto.class));
        return paginatedResponse;

    }

    public static String[] fetchTicketCategoriesNames() throws Exception {
        //the pagination to be enhanced later
        List<TicketCategoryDto> categories = TicketCategoryService.getTicketCategories(0, 10).getContent();
        TicketCategoryDto ticketCategoryDto = new TicketCategoryDto();
        ticketCategoryDto.setName("");
        categories.add(0, ticketCategoryDto);
        return categories.stream().map(TicketCategoryDto::getName).toArray(String[]::new);
    }
}
