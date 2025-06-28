package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerHardwareComponent(
        String component,
        String model,
        int value,
        String valueSuffix
) {}