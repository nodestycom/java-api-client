package dev.astatic.nodestyclient.api.models.vps;

import java.util.List;

public record VpsIoSpeedGraph(
        List<Long> read,
        List<Long> write,
        List<Long> categories
) {}