package dev.astatic.nodestyclient.models.vps;

public record VpsTask(
        String action,
        String progress,
        long startedAt,
        long endedAt
) {}