package dev.astatic.nodestyclient.api.models.user;

import com.google.gson.annotations.SerializedName;

public enum TicketStatus {
    @SerializedName("Open")
    OPEN("Open"),
    @SerializedName("Answered")
    ANSWERED("Answered"),
    @SerializedName("Client-Reply")
    CLIENT_REPLY("Client-Reply"),
    @SerializedName("Closed")
    CLOSED("Closed");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}