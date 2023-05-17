package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubApiService {
    private HttpClient client;
    private String token;

    public GitHubApiService(HttpClient client, String token) {
        this.client = client;
        this.token = token;
    }

    public GitHubUser getUserByUsername(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + username))
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setId(rootNode.get("id").asInt());
        gitHubUser.setName(rootNode.get("name").asText());
        gitHubUser.setEmail(rootNode.get("email").asText());

        return gitHubUser;
    }

}
