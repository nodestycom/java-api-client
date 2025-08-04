package dev.astatic.nodestyclient.models.vps;

public record VpsInfoRam(
        long total,
        long used,
        long free
) {}