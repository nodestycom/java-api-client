package dev.astatic.nodestyclient.api.models.vps;

public record VpsCpuUsage(
        double usage,
        int cores,
        int clockSpeed
) {
}
