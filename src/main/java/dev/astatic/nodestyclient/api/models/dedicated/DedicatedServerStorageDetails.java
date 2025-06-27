package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerStorageDetails(
        String model,
        long total,
        long used,
        long free
) {}