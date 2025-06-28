package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallHistoryEntry(
        long timestamp,
        String ip,
        FirewallHistoryAction action,
        String user
) {
}