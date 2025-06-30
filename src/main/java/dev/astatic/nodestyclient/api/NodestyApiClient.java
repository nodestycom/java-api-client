package dev.astatic.nodestyclient.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import dev.astatic.nodestyclient.service.DedicatedApiService;
import dev.astatic.nodestyclient.service.FirewallApiService;
import dev.astatic.nodestyclient.service.UserApiService;
import dev.astatic.nodestyclient.service.VpsApiService;
import org.asynchttpclient.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class NodestyApiClient implements AutoCloseable {

    private static final String API_BASE_URL = "https://nodesty.com/api";

    private final AsyncHttpClient asyncHttpClient;
    private final Gson gson;
    private final RestClientOptions options;

    private final UserApiService userApiService;
    private final VpsApiService vpsApiService;
    private final FirewallApiService firewallApiService;
    private final DedicatedApiService dedicatedServerApiService;

    public NodestyApiClient(RestClientOptions options) {
        this.options = options;

        AsyncHttpClientConfig clientConfig = new DefaultAsyncHttpClientConfig.Builder()
                .setConnectTimeout(options.getTimeout())
                .setRequestTimeout(options.getTimeout())
                .setPooledConnectionIdleTimeout(options.getTimeout())
                .setReadTimeout(options.getTimeout())
                .setMaxConnections(100)
                .setMaxConnectionsPerHost(50)
                .setFollowRedirect(true)
                .build();

        this.asyncHttpClient = new DefaultAsyncHttpClient(clientConfig);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        ApiFetchFunc fetchFunction = new ApiFetchFunc() {
            @Override
            public <T> CompletableFuture<ApiResponse<T>> fetch(String path, String method, Object body, Class<T> responseDataType) {
                return executeFetch(path, method, body, responseDataType);
            }

            @Override
            public <T> CompletableFuture<ApiResponse<List<T>>> fetch(String path, String method, Object body, TypeToken<List<T>> typeToken) {
                return executeFetch(path, method, body, typeToken.getType());
            }
        };

        this.userApiService = new UserApiService(fetchFunction);
        this.vpsApiService = new VpsApiService(fetchFunction);
        this.firewallApiService = new FirewallApiService(fetchFunction);
        this.dedicatedServerApiService = new DedicatedApiService(fetchFunction);

    }

    private <T> CompletableFuture<ApiResponse<T>> executeFetch(String path, String method, Object body, Type responseType) {
        String url = API_BASE_URL + path;
        String requestBodyString = null;
        if (body != null) {
            requestBodyString = gson.toJson(body);
        }

        BoundRequestBuilder requestBuilder = asyncHttpClient.prepare(method, url)
                .addHeader("Authorization", "PAT " + options.getAccessToken())
                .addHeader("Content-Type", "application/json")
                .setRequestTimeout(Duration.ofDays((int) options.getTimeout().toMillis()));

        if (requestBodyString != null) {
            requestBuilder.setBody(requestBodyString);
        }

        return requestBuilder.execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    String responseBody = response.getResponseBody();
                    try {
                        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                            return gson.fromJson(responseBody, TypeToken.getParameterized(ApiResponse.class, responseType).getType());
                        } else {
                            ApiResponse<T> errorResponse = gson.fromJson(responseBody, TypeToken.getParameterized(ApiResponse.class, responseType).getType());
                            if (errorResponse == null) {
                                errorResponse = new ApiResponse<>(false, "API error: Status Code " + response.getStatusCode() + ", Response: " + responseBody, null);
                            } else if (errorResponse.getError() == null) {
                                errorResponse.setError("API error: Status Code " + response.getStatusCode() + ", Response: " + responseBody);
                            }
                            return errorResponse;
                        }
                    } catch (JsonSyntaxException e) {
                        return new ApiResponse<>(false, "JSON parsing error: " + e.getMessage() + " - Response: " + responseBody, null);
                    } catch (Exception e) {
                        return new ApiResponse<>(false, "Unexpected error while processing request: " + e.getMessage() + " - Response: " + responseBody, null);
                    }
                });
    }

    public UserApiService user() {
        return userApiService;
    }

    public VpsApiService vps() {
        return vpsApiService;
    }

    public FirewallApiService firewall() {
        return firewallApiService;
    }

    public DedicatedApiService dedicatedServer() {
        return dedicatedServerApiService;
    }

    @Override
    public void close() throws IOException {
        if (!asyncHttpClient.isClosed()) {
            asyncHttpClient.close();
        }
    }
}