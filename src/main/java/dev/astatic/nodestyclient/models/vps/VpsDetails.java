package dev.astatic.nodestyclient.models.vps;

import java.util.List;

public record VpsDetails(
        long vpsId,
        long proxmoxId,
        String hostname,
        int osReinstallLimit,
        boolean status,
        VpsInfoVnc vnc,
        VpsInfoOs os,
        VpsInfoDisk disk,
        List<String> ips,
        VpsInfoCpu cpu,
        VpsInfoRam ram,
        VpsInfoInode inode,
        VpsInfoNetspeed netSpeed,
        VpsInfoBandwidth bandwidth
) {}