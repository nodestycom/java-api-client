package dev.astatic.nodestyclient.models.dedicated;

import lombok.Getter;

@Getter
public enum DedicatedServerReinstallStep {
    REBOOTING_SERVER(0),
    PREPARING_BOOT_ENVIRONMENT(1),
    INSTALLING_OPERATING_SYSTEM(2),
    INSTALLATION_COMPLETED(3);

    private final int value;

    DedicatedServerReinstallStep(int value) {
        this.value = value;
    }

    public static DedicatedServerReinstallStep fromValue(int value) {
        for (DedicatedServerReinstallStep step : values()) {
            if (step.value == value) {
                return step;
            }
        }
        throw new IllegalArgumentException("Unknown DedicatedServerReinstallStep value: " + value);
    }
}