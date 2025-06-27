package dev.astatic.nodestyclient.api.models.firewall;

public record FirewallCreateRuleData(
        int port,
        int appId
) {}
