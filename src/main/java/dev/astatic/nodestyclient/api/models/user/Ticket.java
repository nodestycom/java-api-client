package dev.astatic.nodestyclient.api.models.user;

import java.util.List;

public record Ticket(
        String id,
        String subject,
        TicketStatus status,
        String department,
        String priority,
        long createdAt,
        long updatedAt,
        List<TicketMessage> messages
) {}