package dev.astatic.nodestyclient.api.models.user;

public record TicketMessage(
        String id,
        String sender,
        String message,
        long createdAt
) {
}