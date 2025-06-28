package dev.astatic.nodestyclient.api.models.vps;

public record VpsBackup(
        String date,
        String file,
        long createdAt
) {
}