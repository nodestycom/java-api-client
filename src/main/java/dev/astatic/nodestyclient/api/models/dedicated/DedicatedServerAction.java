package dev.astatic.nodestyclient.api.models.dedicated;

import com.google.gson.annotations.SerializedName;

public enum DedicatedServerAction {
    @SerializedName("boot")
    BOOT("boot"),
    @SerializedName("reboot")
    REBOOT("reboot"),
    @SerializedName("shutdown")
    SHUTDOWN("shutdown"),
    @SerializedName("kill")
    KILL("kill");

    private final String value;

    DedicatedServerAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
