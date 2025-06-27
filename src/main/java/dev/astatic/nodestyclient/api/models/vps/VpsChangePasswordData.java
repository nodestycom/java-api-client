package dev.astatic.nodestyclient.api.models.vps;

public record VpsChangePasswordData(
        String username,
        String password
) {}