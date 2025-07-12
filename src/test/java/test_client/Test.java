package test_client;

import dev.astatic.nodestyclient.api.NodestyApiClient;
import dev.astatic.nodestyclient.api.RestClientOptions;
import dev.astatic.nodestyclient.api.models.user.CurrentUser;
import org.jetbrains.annotations.TestOnly;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@TestOnly
public class Test {
    public static void main(String[] args) {
        String token = "TOKEN";

        var options = new RestClientOptions(token)
                .withTimeout(Duration.ofSeconds(45))
                .withRetry(5)
                .withRateLimitOffset(100);
        try (NodestyApiClient client = new NodestyApiClient(options)) {

            CompletableFuture<Void> future = client.user().getCurrentUser()
                    .thenAccept(userApiResponse -> {
                        if (userApiResponse.isSuccess()) {
                            CurrentUser user = userApiResponse.getData();
                            System.out.println("User Info:");
                            System.out.println("ID: " + user.id());
                            System.out.println("Mail: " + user.email());
                            System.out.println("Full Name: " + user.fullName());
                        } else {
                            System.err.println("Err: " + userApiResponse.getError());
                        }
                    })
                    .exceptionally(ex -> {
                        System.err.println("Err: " + ex.getMessage());
                        return null;
                    });

            future.join();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}