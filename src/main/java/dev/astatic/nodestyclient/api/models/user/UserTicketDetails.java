package dev.astatic.nodestyclient.api.models.user;

import java.util.List;

public record UserTicketDetails(
        String id,
        String subject,
        String status,
        String priority,
        String lastReply,
        boolean marked,
        List<TicketMessage> messages
) {}