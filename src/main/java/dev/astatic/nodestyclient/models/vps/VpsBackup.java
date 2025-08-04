package dev.astatic.nodestyclient.models.vps;

public record VpsBackup(
        String date,
        String file,
        long createdAt
) {
}