package dev.astatic.nodestyclient.api.models.vps;

public record VpsBandwidthDetails(
        long total,
        long used,
        long free
) {
}