package dev.astatic.nodestyclient.api.models.vps;

import java.util.List;

public record VpsNetworkSpeedGraph(
        List<Long> download,
        List<Long> upload,
        List<Long> categories
) {}