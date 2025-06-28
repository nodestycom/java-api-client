package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallWhiteList(
        String id,
        String ipAddress,
        long createdAt
) {
}