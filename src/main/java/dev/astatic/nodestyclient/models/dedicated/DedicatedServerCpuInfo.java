package dev.astatic.nodestyclient.models.dedicated;

public record DedicatedServerCpuInfo(
        String model,
        int speed,
        int turboSpeed,
        int cores,
        int threads
) {}