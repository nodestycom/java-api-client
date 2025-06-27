package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerHardwareComponent(
        String component,
        String model,
        double value,
        String valueSuffix
) {}