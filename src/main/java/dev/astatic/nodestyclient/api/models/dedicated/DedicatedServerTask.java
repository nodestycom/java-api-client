package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerTask(
        String action,
        String progress,
        long startedAt,
        long updatedAt
) {}