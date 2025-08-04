package dev.astatic.nodestyclient.models.vps;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public enum VpsAction {
    @SerializedName("start")
    START("start"),
    @SerializedName("stop")
    STOP("stop"),
    @SerializedName("restart")
    RESTART("restart"),
    @SerializedName("poweroff")
    POWEROFF("poweroff"),;

    private final String value;

    VpsAction(String value) {
        this.value = value;
    }

}