package dev.astatic.nodestyclient.models.user;

public record UserServiceAddon(
        String name,
        double recurringAmount,
        String billingCycle,
        String status,
        long registerDate,
        long nextDueDate
) {}