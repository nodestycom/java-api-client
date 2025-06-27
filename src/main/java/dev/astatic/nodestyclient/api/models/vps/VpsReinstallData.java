package dev.astatic.nodestyclient.api.models.vps;

public record VpsReinstallData(
        String password,
        int osId
) {}