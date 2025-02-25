package org.hahen.ticketEase.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;
import org.hahen.ticketEase.enums.HttpMethods;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Http {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();
    public static HttpResponse<String> sendRequest( HttpMethods method, String url, Map<String, String> headers , Object jsonBody) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url));

        requestBuilder.header("Content-Type", "application/json");
        String authToken = GlobalVariables.getAuthToken();
        if (authToken != null && !authToken.isEmpty()) {
            requestBuilder.header("Authorization", "Bearer " + authToken);
        }



        String jsonString = null;
        if (jsonBody != null) {
            jsonString = objectMapper.writeValueAsString(jsonBody);
            System.out.println(jsonString);
        }

        switch (method) {
            case GET -> requestBuilder.GET();
            case POST -> {
                if (jsonString != null) {
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonString));
                } else {
                    requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
                }
            }
            case PUT -> {
                if (jsonString != null) {
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonString));
                } else {
                    requestBuilder.PUT(HttpRequest.BodyPublishers.noBody());
                }
            }
            case DELETE -> requestBuilder.DELETE();
            default -> throw new UnsupportedOperationException("HTTP method not supported: " + method);
        }


        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        HttpRequest request = requestBuilder.build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}


