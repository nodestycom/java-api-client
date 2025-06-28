package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoRam(
        long total,
        long used,
        long free
) {}