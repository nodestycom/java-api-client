package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallStatistics(
        String timestamp,
        String totalPassTraffic,
        String totalDropTraffic
) {}