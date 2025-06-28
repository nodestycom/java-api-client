package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoDisk(
        long limit,
        long used,
        long free,
        int percent
) {}