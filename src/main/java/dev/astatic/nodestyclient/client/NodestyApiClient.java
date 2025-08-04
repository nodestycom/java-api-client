package dev.astatic.nodestyclient.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.astatic.nodestyclient.service.DedicatedService;
import dev.astatic.nodestyclient.service.FirewallService;
import dev.astatic.nodestyclient.service.UserService;
import dev.astatic.nodestyclient.service.VpsService;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class NodestyApiClient implements AutoCloseable {

    private static final String API_BASE_URL = "https://nodesty.com/api/";
    private final Retrofit retrofit;
    private final OkHttpClient okHttpClient;

    @Getter
    private final UserService user;
    @Getter
    private final VpsService vps;
    @Getter
    private final FirewallService firewall;
    @Getter
    private final DedicatedService dedicated;

    public NodestyApiClient(ClientOptions options) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (options.isLoggingEnabled()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
                    System.out.println("Nodesty API Logger: " + message)
            );

            switch (options.getLogLevel()) {
                case NONE -> loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                case BASIC -> {
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                    httpClientBuilder.addInterceptor(loggingInterceptor);
                }
                case HEADERS ->{
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    httpClientBuilder.addInterceptor(loggingInterceptor);
                }
                case BODY -> {
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    httpClientBuilder.addInterceptor(loggingInterceptor);
                }
            }
        }

        httpClientBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "PAT " + options.getAccessToken())
                    .header("User-Agent", "Nodesty-Java-Client/1.0.2")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        httpClientBuilder.connectTimeout(options.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
        httpClientBuilder.readTimeout(options.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
        httpClientBuilder.writeTimeout(options.getTimeout().toMillis(), TimeUnit.MILLISECONDS);

        this.okHttpClient = httpClientBuilder.build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        this.user = retrofit.create(UserService.class);
        this.vps = retrofit.create(VpsService.class);
        this.firewall = retrofit.create(FirewallService.class);
        this.dedicated = retrofit.create(DedicatedService.class);
    }

    @Override
    public void close() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }
}