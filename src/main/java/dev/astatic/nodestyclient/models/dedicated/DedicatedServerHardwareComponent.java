package dev.astatic.nodestyclient.models.dedicated;

public record DedicatedServerHardwareComponent(
        String component,
        String model,
        int value,
        String valueSuffix
) {}