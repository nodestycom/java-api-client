package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoVnc(
        boolean enabled,
        String ip,
        String port,
        String password
) {}
