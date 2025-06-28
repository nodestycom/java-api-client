package dev.astatic.nodestyclient.api.models.vps;

import com.google.gson.annotations.SerializedName;

public enum VpsAction {
    @SerializedName("boot")
    BOOT("boot"),
    @SerializedName("reboot")
    REBOOT("reboot"),
    @SerializedName("shutdown")
    SHUTDOWN("shutdown"),
    @SerializedName("kill")
    KILL("kill");

    private final String value;

    VpsAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}