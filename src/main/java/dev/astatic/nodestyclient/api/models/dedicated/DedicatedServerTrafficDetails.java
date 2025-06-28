package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerTrafficDetails(
        long total,
        long used,
        long free
) {
}