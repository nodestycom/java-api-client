package dev.astatic.nodestyclient.api.models.dedicated;

import java.util.List;

public record DedicatedServerDetails(
        String dedicatedId,
        boolean status,
        List<String> availableActions,
        String mainboard,
        int ram,
        int disk,
        DedicatedServerCpuInfo cpu
) {}