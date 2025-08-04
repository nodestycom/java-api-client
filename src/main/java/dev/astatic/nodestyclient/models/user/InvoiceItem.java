package dev.astatic.nodestyclient.models.user;

public record InvoiceItem(
        long id,
        String type,
        String description,
        double amount
) {}