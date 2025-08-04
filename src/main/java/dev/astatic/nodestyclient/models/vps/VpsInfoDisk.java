package dev.astatic.nodestyclient.models.vps;

public record VpsInfoDisk(
        long limit,
        long used,
        long free,
        int percent
) {}