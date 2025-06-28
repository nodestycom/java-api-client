package dev.astatic.nodestyclient.api.models.user;

public record UserStats(
        int activeServices,
        int unpaidInvoices,
        double balance,
        int activeTickets
) {}