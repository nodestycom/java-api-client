package dev.astatic.nodestyclient.client;

import lombok.Getter;

import java.time.Duration;

public class ClientOptions {

    @Getter
    private String accessToken;
    private Integer retry;
    private Duration timeout;
    private Integer rateLimitOffset;
    private Boolean enableLogging;
    private LogLevel logLevel;

    public enum LogLevel {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public ClientOptions(String accessToken) {
        this.accessToken = accessToken;
        this.retry = 3;
        this.timeout = Duration.ofSeconds(30);
        this.rateLimitOffset = 50;
        this.enableLogging = true;
        this.logLevel = LogLevel.BODY;
    }

    public ClientOptions withRetry(int retry) {
        this.retry = retry;
        return this;
    }

    public ClientOptions withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public ClientOptions withRateLimitOffset(int rateLimitOffset) {
        this.rateLimitOffset = rateLimitOffset;
        return this;
    }

    public ClientOptions withLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
        return this;
    }

    public ClientOptions withLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public int getRetry() {
        return retry != null ? retry : 3;
    }

    public Duration getTimeout() {
        return timeout != null ? timeout : Duration.ofSeconds(30);
    }

    public int getRateLimitOffset() {
        return rateLimitOffset != null ? rateLimitOffset : 50;
    }

    public boolean isLoggingEnabled() {
        return enableLogging != null ? enableLogging : true;
    }

    public LogLevel getLogLevel() {
        return logLevel != null ? logLevel : LogLevel.BASIC;
    }
}