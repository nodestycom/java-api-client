package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerCpuDetails(
        String model,
        int speed,
        int turboSpeed,
        int cores,
        int threads
) {
}