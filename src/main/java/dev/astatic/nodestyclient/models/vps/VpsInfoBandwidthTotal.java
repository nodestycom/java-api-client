package dev.astatic.nodestyclient.models.vps;

public record VpsInfoBandwidthTotal(
        long usage,
        long in,
        long out
) {}