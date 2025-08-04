package dev.astatic.nodestyclient.models.dedicated;

import com.google.gson.annotations.SerializedName;

public enum DedicatedServerAction {
    @SerializedName("setPowerOff")
    SET_POWER_OFF("setPowerOff"),
    @SerializedName("setPowerOn")
    SET_POWER_ON("setPowerOn"),
    @SerializedName("setPowerReset")
    SET_POWER_RESET("setPowerReset");

    private final String value;

    DedicatedServerAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}