package dev.astatic.nodestyclient.models.user;

public record UserTicketSummary(
        String id,
        String subject,
        String status,
        String priority,
        String lastReply,
        boolean marked
) {}
