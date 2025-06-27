package dev.astatic.nodestyclient.api.models.user;

import com.google.gson.annotations.SerializedName;

public enum SessionOs {
    @SerializedName("Desktop")
    DESKTOP("Desktop"),
    @SerializedName("Mobile")
    MOBILE("Mobile");

    private final String value;

    SessionOs(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}