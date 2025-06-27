package dev.astatic.nodestyclient.api.models.user;

import com.google.gson.annotations.SerializedName;

public enum InvoiceStatus {
    @SerializedName("Unpaid")
    UNPAID("Unpaid"),
    @SerializedName("Paid")
    PAID("Paid"),
    @SerializedName("Cancelled")
    CANCELLED("Cancelled"),
    @SerializedName("Refunded")
    REFUNDED("Refunded");

    private final String value;

    InvoiceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
