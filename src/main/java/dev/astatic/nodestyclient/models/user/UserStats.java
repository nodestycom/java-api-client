package dev.astatic.nodestyclient.models.user;

public record UserStats(
        int activeServices,
        int unpaidInvoices,
        double balance,
        int activeTickets
) {}