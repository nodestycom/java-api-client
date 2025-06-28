package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallStatistics(
        long timestamp,
        String totalTraffic,
        String attackTraffic
) {
}
