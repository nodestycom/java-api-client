package dev.astatic.nodestyclient.api.models.dedicated;

import java.util.List;

public record DedicatedServerDetails(
        String hostname,
        List<String> ipAddresses,
        String status,
        String os,
        long createdAt,
        long updatedAt,
        DedicatedServerCpuDetails cpu,
        DedicatedServerRamDetails ram,
        List<DedicatedServerStorageDetails> storage,
        DedicatedServerNetworkSpeed netspeed,
        DedicatedServerTrafficDetails traffic
) {}