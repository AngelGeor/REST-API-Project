package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FreshDeskApiService {

    private static final String FRESHDESK_API_URL_START = "https://";
    private static final String FRESHDESK_API_URL_END = ".freshdesk.com/api/v2";

    private HttpClient client;
    private String apiKey;
    private String subdomain;

    public FreshDeskApiService(HttpClient client, String apiKey, String subdomain) {
        this.client = client;
        this.apiKey = apiKey;
        this.subdomain = subdomain;
    }

    public void createOrUpdateContact(GitHubUser user) throws Exception {

        String auth = apiKey + ":X";
        byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
        String authEncoded = Base64.getEncoder().encodeToString(authBytes);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FRESHDESK_API_URL_START + subdomain + FRESHDESK_API_URL_END + "/contacts"))
                .header("Authorization", "Basic " + authEncoded)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(user.toFreshDesk()))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            System.out.println("Contact created successfully");
        } else if (response.statusCode() == 409) {

            JsonNode rootNode = new ObjectMapper().readTree(response.body());
            String contactId = rootNode.get("errors").get(0).get("additional_info").get("user_id").toString();

            updateContact(contactId, user);
        } else {
            System.err.println("Error creating/updating contact: " + response.body());
        }
    }

    private void updateContact(String contactId, GitHubUser user) throws Exception {

        String auth = apiKey + ":X";
        byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
        String authEncoded = Base64.getEncoder().encodeToString(authBytes);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FRESHDESK_API_URL_START + subdomain + FRESHDESK_API_URL_END + "/contacts/" + contactId))
                .header("Authorization", "Basic " + authEncoded)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(user.toFreshDesk()))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Contact updated successfully");
        } else {
            System.err.println("Error updating contact: " + response.body());
        }
    }
}

