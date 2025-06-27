package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerRamDetails(
        long total,
        long used,
        long free
) {}