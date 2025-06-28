package dev.astatic.nodestyclient.api.models.vps;

import java.util.List;

public record VpsDetails(
        String hostname,
        List<String> ipAddresses,
        String status,
        String os,
        long createdAt,
        long updatedAt,
        VpsCpuUsage cpu,
        VpsRamUsage ram,
        VpsInodeUsage inode,
        VpsNetworkSpeed netspeed,
        VpsBandwidthDetails bandwidth
) {
}
