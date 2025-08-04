package dev.astatic.nodestyclient.models.firewall;

public record FirewallCreateRuleData(
        int port,
        int appId
) {}
