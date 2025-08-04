package dev.astatic.nodestyclient.models.dedicated;

public record DedicatedServerTask(
        String action,
        long startedAt,
        long updatedAt
) {}