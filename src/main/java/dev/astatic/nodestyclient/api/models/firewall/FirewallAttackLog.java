package dev.astatic.nodestyclient.api.models.firewall;

import java.util.List;

public record FirewallAttackLog(
        long startedAt,
        long endedAt,
        List<String> vectors,
        long peak
) {}