package dev.astatic.nodestyclient.models.vps;

public record VpsInfoInode(
        long limit,
        long used,
        long free,
        int percent
) {}