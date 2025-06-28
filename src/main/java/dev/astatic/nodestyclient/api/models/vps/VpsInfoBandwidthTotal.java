package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoBandwidthTotal(
        long usage,
        long in,
        long out
) {}