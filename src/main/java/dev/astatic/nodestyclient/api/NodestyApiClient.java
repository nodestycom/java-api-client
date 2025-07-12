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

        AsyncHttpClientConfig clientConfig = new DefaultAsyncHttpClientConfig
                .Builder()
                .setConnectTimeout(options.getTimeout())
                .setRequestTimeout(options.getTimeout())
                .setPooledConnectionIdleTimeout(options.getTimeout())
                .setReadTimeout(options.getTimeout())
                .setMaxConnections(100)
                .setMaxConnectionsPerHost(50)
                .setFollowRedirect(true)
                .build();

        this.asyncHttpClient = new DefaultAsyncHttpClient(clientConfig);
        this.gson = new GsonBuilder().setPrettyPrinting().create();

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

        BoundRequestBuilder requestBuilder = asyncHttpClient.
                prepare(method, url).
                addHeader("Authorization", "PAT " + options.
                getAccessToken()).
                addHeader("Content-Type", "application/json");


        if (requestBodyString != null) {
            requestBuilder.setBody(requestBodyString);
        }

        return requestBuilder.execute().toCompletableFuture().thenApply(response -> {
            String responseBody = response.getResponseBody();
            int statusCode = response.getStatusCode();

            try {
                if (statusCode >= 200 && statusCode < 300) {
                    try {
                        ApiResponse<T> apiResponse = gson.fromJson(responseBody, TypeToken.getParameterized(ApiResponse.class, responseType).getType());

                        if (apiResponse != null && apiResponse.isSuccess()) {
                            return apiResponse;
                        }

                    } catch (JsonSyntaxException e) {
                    }

                    try {
                        T data = gson.fromJson(responseBody, responseType);
                        return new ApiResponse<>(true, null, data);
                    } catch (JsonSyntaxException e) {
                        return new ApiResponse<>(false, "JSON ayrıştırma hatası: Beklenen 'ApiResponse' veya direkt veri formatında değil. Hata: " + e.getMessage() + " - Yanıt: " + responseBody, null);
                    }

                } else {
                    ApiResponse<T> apiResponse;
                    try {
                        apiResponse = gson.fromJson(responseBody, TypeToken.getParameterized(ApiResponse.class, responseType).getType());
                    } catch (JsonSyntaxException e) {
                        apiResponse = new ApiResponse<>();
                        apiResponse.setSuccess(false);
                        apiResponse.setError("API hatası (HTTP " + statusCode + "): JSON formatı beklenenden farklı veya ayrıştırma hatası. Yanıt: " + responseBody + " - Hata: " + e.getMessage());
                        apiResponse.setData(null);
                        return apiResponse;
                    }

                    if (apiResponse != null && !apiResponse.isSuccess()) {
                        if (apiResponse.getError() == null || apiResponse.getError().isEmpty()) {
                            apiResponse.setError("API hatası (HTTP " + statusCode + "): Sunucu hata mesajı sağlamadı. Yanıt: " + responseBody);
                        }
                        return apiResponse;
                    } else {
                        return new ApiResponse<>(false, "API hatası (HTTP " + statusCode + "): Beklenmeyen yanıt yapısı. Yanıt: " + responseBody, null);
                    }
                }
            } catch (JsonSyntaxException e) {
                return new ApiResponse<>(false, "JSON ayrıştırma hatası: " + e.getMessage() + " - Yanıt: " + responseBody, null);
            } catch (Exception e) {
                return new ApiResponse<>(false, "İstek işlenirken beklenmeyen hata: " + e.getMessage() + " - Yanıt: " + responseBody, null);
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