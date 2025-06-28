package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallAttackLog(
        long time,
        String type,
        String sourceIp,
        String destinationPort,
        String action,
        String protocol
) {
}