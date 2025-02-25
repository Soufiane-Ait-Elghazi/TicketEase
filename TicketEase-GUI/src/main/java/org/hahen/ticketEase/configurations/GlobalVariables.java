package org.hahen.ticketEase.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalVariables {
    public static final String BASE_API_URL = "http://localhost:8888/ticketEase";
    private static String authToken = "";
    public static String getAuthToken() {
        return authToken;
    }
    public static void setAuthToken(String token) {
        authToken = token;
    }
    public static final int PAGE_SIZE = 10;



}
