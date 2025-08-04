package dev.astatic.nodestyclient.models.user;

import java.util.List;

public record UserInvoiceDetails(
        long id,
        long dueDate,
        Long datePaid,
        double subTotal,
        double total,
        String status,
        double appliedBalance,
        List<InvoiceItem> items
) {}