package dev.astatic.nodestyclient.api.models.vps;


import java.util.Map;

public record VpsGraphs(
        long avgDownload,
        long avgUpload,
        long avgIoRead,
        long avgIoWrite,
        Map<String, Double> cpuUsage,
        Map<String, Long> inodeUsage,
        Map<String, Long> ramUsage,
        Map<String, Long> diskUsage,
        VpsIoSpeedGraph ioSpeed,
        VpsNetworkSpeedGraph networkSpeed
) {}