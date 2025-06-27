package dev.astatic.nodestyclient.api.models.user;

import java.util.List;

public record Invoice(
        String id,
        String invoiceNumber,
        long dateCreated,
        long dateDue,
        Long datePaid,
        double subTotal,
        double total,
        InvoiceStatus status,
        double appliedBalance,
        List<InvoiceItem> items
) {}