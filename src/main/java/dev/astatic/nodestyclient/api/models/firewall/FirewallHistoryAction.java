package dev.astatic.nodestyclient.api.models.firewall;

import com.google.gson.annotations.SerializedName;

public enum FirewallHistoryAction {
    @SerializedName("block")
    BLOCK("block"),
    @SerializedName("unblock")
    UNBLOCK("unblock"),
    @SerializedName("whitelist")
    WHITELIST("whitelist"),
    @SerializedName("unwhitelist")
    UNWHITELIST("unwhitelist"),
    @SerializedName("clear")
    CLEAR("clear"),
    @SerializedName("rule_added")
    RULE_ADDED("rule_added"),
    @SerializedName("rule_removed")
    RULE_REMOVED("rule_removed");

    private final String value;

    FirewallHistoryAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}