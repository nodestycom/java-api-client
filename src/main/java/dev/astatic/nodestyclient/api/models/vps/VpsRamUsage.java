package dev.astatic.nodestyclient.api.models.vps;

public record VpsRamUsage(
        long total,
        long used,
        long free
) {
}