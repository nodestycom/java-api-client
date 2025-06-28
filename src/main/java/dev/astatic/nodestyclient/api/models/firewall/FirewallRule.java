package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallRule(
        int id,
        String protocol,
        String service,
        int port
) {
}