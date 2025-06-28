package dev.astatic.nodestyclient.api.models.vps;

public record VpsInodeUsage(
        long total,
        long used,
        long free
) {
}