package dev.astatic.nodestyclient.api.models.dedicated;

public record DedicatedServerReinstallStatus(
        boolean completed,
        DedicatedServerReinstallStep currentStep
) {
}