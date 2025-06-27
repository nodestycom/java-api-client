package dev.astatic.nodestyclient.api.models.user;

public record InvoiceItem(
        String description,
        double amount
) {}