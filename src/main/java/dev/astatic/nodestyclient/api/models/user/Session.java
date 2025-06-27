package dev.astatic.nodestyclient.api.models.user;

public record Session(
        String id,
        String ip,
        String location,
        SessionOs os,
        String platform,
        String browser,
        long lastActivity
) {}