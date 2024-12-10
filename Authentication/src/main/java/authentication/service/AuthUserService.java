package authentication.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AuthUserService {

    private final WebClient webClient;
    private static final String CLIENTSECRET = "3PNTN4xR5nQmgtmJleJdGZ5AS6pcFVQn";

    public AuthUserService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8006")
                .build();
    }

    public String login(String username, String password) {
        try {
            System.out.println(username);
            System.out.println(password);
            // Make the POST request to get the response containing the access token
            String response = webClient.post()
                    .uri("/realms/journal_system/protocol/openid-connect/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue("grant_type=password&client_id=auth&client_secret=" + CLIENTSECRET + "&username=" + username + "&password=" + password)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Blocking to get the response

            // Extract the access token from the response using the extractAccessToken method
            return extractAccessToken(response);

        } catch (Exception e) {
            throw new RuntimeException("Error occurred during login: " + e.getMessage(), e);
        }
    }


    public String validateToken(String token) {
        return webClient.get()
                .uri("/userinfo")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Returnerar användarinformation
    }

    // Hämta Access Token med client_credentials
    public String getAccessToken() {
        try {
            // Gör en POST-begäran för att hämta access token
            String response = webClient.post()
                    .uri("/realms/journal_system/protocol/openid-connect/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue("grant_type=client_credentials&client_id=auth&client_secret=" + CLIENTSECRET)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Vänta på svaret från servern

            // Extrahera access_token från JSON-strängen (manuell stränghantering)
            String accessToken = extractAccessToken(response);

            return accessToken; // Returnera endast access_token
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get access token: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the access token response.", e);
        }
    }

    // Enkel metod för att extrahera access_token från JSON-svaret
    private String extractAccessToken(String response) {
        // Hitta start- och slutpositionen för "access_token" i JSON-strängen
        String prefix = "\"access_token\":\"";
        String suffix = "\"";

        int startIdx = response.indexOf(prefix) + prefix.length();
        int endIdx = response.indexOf(suffix, startIdx);

        // Extrahera och returnera access_token
        if (endIdx != -1) {
            return response.substring(startIdx, endIdx);
        } else {
            throw new RuntimeException("Access token not found in the response.");
        }
    }

    // Skapa användare via Keycloak Admin API
    public void createUser(String username, String firstName, String lastName, String password) {
        try {
            // Hämta access token
            String accessToken = getAccessToken();
            System.out.println(username);
            System.out.println(password);
            System.out.println(firstName);
            System.out.println(lastName);

            // Skicka request för att skapa användare
            webClient.post()
                    .uri("/admin/realms/journal_system/users")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + accessToken) // Använd access token för autentisering
                    .bodyValue("{\n" +
                            "  \"username\": \"" + username + "\",\n" +
                            "  \"enabled\": true,\n" +
                            "  \"email\": \"" + username + "\",\n" +
                            "  \"firstName\": \"" + firstName + "\",\n" +
                            "  \"lastName\": \"" + lastName + "\",\n" +
                            "  \"emailVerified\": true,\n" +
                            "  \"credentials\": [\n" +
                            "    {\n" +
                            "      \"type\": \"password\",\n" +
                            "      \"value\": \"" + password + "\",\n" +
                            "      \"temporary\": false\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}")
                    .retrieve()
                    .toBodilessEntity() // Returnera inget innehåll (bara status)
                    .block(); // Vänta på att användaren skapas
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to create user: " + e.getResponseBodyAsString());
        }
    }
}