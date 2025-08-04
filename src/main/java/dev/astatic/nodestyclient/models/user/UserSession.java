package dev.astatic.nodestyclient.models.user;

public record UserSession(
        String id,
        String ip,
        String location,
        String os,
        String platform,
        String lastSeen
) {}