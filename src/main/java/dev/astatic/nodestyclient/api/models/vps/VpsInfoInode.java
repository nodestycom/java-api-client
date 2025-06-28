package dev.astatic.nodestyclient.api.models.vps;

public record VpsInfoInode(
        long limit,
        long used,
        long free,
        int percent
) {}