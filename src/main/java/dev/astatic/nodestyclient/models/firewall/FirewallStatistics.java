package dev.astatic.nodestyclient.models.firewall;

public record FirewallStatistics(
        String timestamp,
        String totalPassTraffic,
        String totalDropTraffic
) {}