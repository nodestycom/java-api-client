package dev.astatic.nodestyclient.models.user;

public record UserInvoiceSummary(
        long id,
        long dueDate,
        Long datePaid,
        double subTotal,
        double total,
        String status,
        double appliedBalance
) {}