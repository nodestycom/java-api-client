package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerTask(
        String action,
        long startedAt,
        long updatedAt
) {}