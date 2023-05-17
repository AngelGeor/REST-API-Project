package org.example;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter GitHub username: ");
        String username = scanner.nextLine();

        String gitHubToken = System.getenv("GITHUB_TOKEN");
        HttpClient client = HttpClient.newBuilder().build();
        GitHubApiService gitHubApiService = new GitHubApiService(client, gitHubToken);
        GitHubUser gitHubUser = gitHubApiService.getUserByUsername(username);

        String apiKey = System.getenv("FRESHDESK_TOKEN");
        System.out.println("Enter Freshdesk subdomain:");

        String subdomain = scanner.nextLine();
        FreshDeskApiService freshdeskApiService = new FreshDeskApiService(client, apiKey, subdomain);


        freshdeskApiService.createOrUpdateContact(gitHubUser);


    }
}