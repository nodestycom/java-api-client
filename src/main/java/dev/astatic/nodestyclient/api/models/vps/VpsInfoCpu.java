package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoCpu(
        String manu,
        long limit,
        long used,
        long free,
        double percent,
        int cores
) {}