package dev.astatic.nodestyclient.api.models.user;

public record InvoiceItem(
        long id,
        String type,
        String description,
        double amount
) {}