package org.hahen.ticketEase.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.pages.DashBoardPage;

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
    public static  long TICKET_ID = 0;

    public static  DashBoardPage GLOBAL_DashBoard;
    public static boolean  IT_SUPPORT_USER;



}
