package dev.astatic.nodestyclient.models.dedicated;

public record DedicatedServerReinstallStatus(
        boolean completed,
        int step
) {}