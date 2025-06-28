package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerReinstallData(
        String password,
        int osId
) {}