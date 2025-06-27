package dev.astatic.nodestyclient.api.models.vps;

public record VpsTask(
        String action,
        String progress,
        long startedAt,
        long updatedAt
) {}