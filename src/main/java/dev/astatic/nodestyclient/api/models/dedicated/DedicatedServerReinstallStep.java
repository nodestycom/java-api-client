package dev.astatic.nodestyclient.api.models.dedicated;

import com.google.gson.annotations.SerializedName;

public enum DedicatedServerReinstallStep {
    @SerializedName("0")
    REBOOTING_SERVER(0),
    @SerializedName("1")
    PREPARING_BOOT_ENVIRONMENT(1),
    @SerializedName("2")
    INSTALLING_OPERATING_SYSTEM(2),
    @SerializedName("3")
    INSTALLATION_COMPLETED(3);

    private final int value;

    DedicatedServerReinstallStep(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}