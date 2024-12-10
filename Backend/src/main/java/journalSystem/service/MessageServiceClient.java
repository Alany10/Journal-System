package journalSystem.service;

import journalSystem.viewModel.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Service
public class MessageServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String MESSAGE_SERVICE_BASE_URL = "https://localhost:8003/message"; // URL för Message-tjänsten

    // Hämta alla meddelanden
    public List<MessageDTO> getAllMessages() {
        String url = MESSAGE_SERVICE_BASE_URL + "/getAll";
        ResponseEntity<List<MessageDTO>> response = sendRequest(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MessageDTO>>() {});
        return response.getBody();
    }

    // Hämta ett meddelande med specifikt ID
    public MessageDTO getMessageById(int id) {
        String url = MESSAGE_SERVICE_BASE_URL + "/get/" + id;
        ResponseEntity<MessageDTO> response = sendRequest(url, HttpMethod.GET, null, MessageDTO.class);
        return response.getBody();
    }

    // Skapa ett nytt meddelande
    public MessageDTO createMessage(MessageDTO messageDTO) {
        String url = MESSAGE_SERVICE_BASE_URL + "/create";
        ResponseEntity<MessageDTO> response = sendRequest(url, HttpMethod.POST, messageDTO, MessageDTO.class);
        return response.getBody();
    }

    // Uppdatera ett meddelande
    public MessageDTO updateMessage(int id, MessageDTO messageDTO) {
        String url = MESSAGE_SERVICE_BASE_URL + "/update/" + id;
        sendRequest(url, HttpMethod.PUT, messageDTO, Void.class);
        return messageDTO; // Returnera det uppdaterade meddelandet
    }

    // Markera ett meddelande som läst
    public MessageDTO readMessage(int id) {
        String url = MESSAGE_SERVICE_BASE_URL + "/read/" + id;
        ResponseEntity<MessageDTO> response = sendRequest(url, HttpMethod.PUT, null, MessageDTO.class);
        return response.getBody();
    }

    // Hämta alla mottagna meddelanden för en användare
    public List<MessageDTO> getAllReceived(String email) {
        String url = MESSAGE_SERVICE_BASE_URL + "/getAllReceived/" + email;
        ResponseEntity<List<MessageDTO>> response = sendRequest(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MessageDTO>>() {});
        return response.getBody();
    }

    // Hämta alla olästa meddelanden för en användare
    public List<MessageDTO> getAllUnread(String email) {
        String url = MESSAGE_SERVICE_BASE_URL + "/getAllUnread/" + email;
        ResponseEntity<List<MessageDTO>> response = sendRequest(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MessageDTO>>() {});
        return response.getBody();
    }

    // Hämta alla skickade meddelanden för en användare
    public List<MessageDTO> getAllSent(String email) {
        String url = MESSAGE_SERVICE_BASE_URL + "/getAllSent/" + email;
        ResponseEntity<List<MessageDTO>> response = sendRequest(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MessageDTO>>() {});
        return response.getBody();
    }

    // Ta bort ett meddelande
    public void deleteMessage(int id) {
        String url = MESSAGE_SERVICE_BASE_URL + "/delete/" + id;
        sendRequest(url, HttpMethod.DELETE, null, Void.class);
    }

    // Generisk metod för att skicka HTTP-begäran och få svar
    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, Object requestBody, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, method, entity, responseType);
    }

    // Generisk metod för att skicka HTTP-begäran och få svar när det gäller parametriserade typer som List<MessageDTO>
    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, Object requestBody, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, method, entity, responseType);
    }
}
