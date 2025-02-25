package org.hahen.ticketEase.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.enums.HttpMethods;
import org.hahen.ticketEase.models.LoginFormDto;
import org.hahen.ticketEase.models.TokenDto;
import org.hahen.ticketEase.network.Http;

import java.net.http.HttpResponse;

public class AuthenticationService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TokenDto login(LoginFormDto loginFormDto) throws Exception {
        HttpResponse<String> response = Http.sendRequest(
                HttpMethods.POST,
                GlobalVariables.BASE_API_URL + "/auth/login",
                null,
                loginFormDto
        );
        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch tickets. HTTP Status: " + response.statusCode());
        }
        TokenDto tokenDto = objectMapper.readValue(response.body(),
                objectMapper.getTypeFactory().constructType(TokenDto.class));
        GlobalVariables.setAuthToken(tokenDto.getAccessToken());
        return tokenDto;
    }

    public static void logOut() throws Exception {
        GlobalVariables.setAuthToken(null);
    }

}
