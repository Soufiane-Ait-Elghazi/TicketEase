package org.hahen.ticketEase.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahen.ticketEase.configurations.GlobalVariables;

import java.util.Base64;


public class JwtTools {

        public static String getPayloadValue(String key) {
            try {
                String[] parts = GlobalVariables.getAuthToken().split("\\.");
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Invalid JWT token");
                }

                String payloadJson = new String(Base64.getDecoder().decode(parts[1]));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(payloadJson);
                JsonNode valueNode = jsonNode.get(key);
                return valueNode != null ? valueNode.asText() : null;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    }

