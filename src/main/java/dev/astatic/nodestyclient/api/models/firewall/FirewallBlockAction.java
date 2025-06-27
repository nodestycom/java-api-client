package dev.astatic.nodestyclient.api.models.firewall;

import com.google.gson.annotations.SerializedName;

public enum FirewallBlockAction {
    @SerializedName("block")
    BLOCK("block"),
    @SerializedName("unblock")
    UNBLOCK("unblock");

    private final String value;

    FirewallBlockAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}