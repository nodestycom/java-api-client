package dev.astatic.nodestyclient.models.dedicated;

public record DedicatedServerReinstallData(
        String password,
        int osId
) {}