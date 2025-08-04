package dev.astatic.nodestyclient.models.firewall;

public record FirewallRule(
        int id,
        String protocol,
        String service,
        int port
) {}