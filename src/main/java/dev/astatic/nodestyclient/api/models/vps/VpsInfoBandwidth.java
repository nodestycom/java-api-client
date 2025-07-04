package dev.astatic.nodestyclient.api.models.vps;

import java.util.List;

public record VpsInfoBandwidth(
        VpsInfoBandwidthTotal total,
        List<Long> usage,
        List<Long> in,
        List<Long> out,
        List<String> categories
) {}