package dev.astatic.nodestyclient;

import dev.astatic.nodestyclient.client.NodestyApiClient;
import dev.astatic.nodestyclient.client.ClientOptions;
import io.reactivex.rxjava3.disposables.Disposable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {

        String token = "token";

        ClientOptions options = new ClientOptions(token)
                .withTimeout(Duration.ofSeconds(30))
                .withLogging(true)
                .withLogLevel(ClientOptions.LogLevel.BODY);

        try (NodestyApiClient client = new NodestyApiClient(options)) {

            CountDownLatch latch = new CountDownLatch(1);

            Disposable disposable = client.getUser().getCurrentUser()
                    .subscribe(
                            user -> {
                                if (user != null) {
                                    System.out.println("Ad Soyad: " + user.fullName());
                                    System.out.println("E-posta: " + user.email());
                                } else {
                                    System.err.println("API'dan kullanıcı bilgisi gelmedi");
                                }
                                latch.countDown();
                            },
                            error -> {
                                latch.countDown();
                            }
                    );

            boolean completed = latch.await(45, TimeUnit.SECONDS);

            if (!completed) {
                System.err.println("timeout");
                disposable.dispose();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}