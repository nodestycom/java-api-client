package dev.astatic.nodestyclient.api;

import lombok.Getter;

import java.time.Duration;

public class RestClientOptions {

    @Getter
    private String accessToken;
    private Integer retry;
    private Duration timeout;
    private Integer rateLimitOffset;

    public RestClientOptions(String accessToken) {
        this.accessToken = accessToken;
        this.retry = 3;
        this.timeout = Duration.ofSeconds(30);
        this.rateLimitOffset = 50;
    }

    public RestClientOptions withRetry(int retry) { this.retry = retry; return this; }
    public RestClientOptions withTimeout(Duration timeout) { this.timeout = timeout; return this; }
    public RestClientOptions withRateLimitOffset(int rateLimitOffset) { this.rateLimitOffset = rateLimitOffset; return this; }

    public int getRetry() { return retry != null ? retry : 3; }
    public Duration getTimeout() { return timeout != null ? timeout : Duration.ofSeconds(30); }
    public int getRateLimitOffset() { return rateLimitOffset != null ? rateLimitOffset : 50; }
}
