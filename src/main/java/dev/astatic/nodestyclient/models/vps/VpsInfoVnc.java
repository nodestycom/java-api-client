package dev.astatic.nodestyclient.models.vps;

public record VpsInfoVnc(
        boolean enabled,
        String ip,
        String port,
        String password
) {}
