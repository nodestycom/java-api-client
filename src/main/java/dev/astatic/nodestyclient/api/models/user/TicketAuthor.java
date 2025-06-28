package dev.astatic.nodestyclient.api.models.user;

public record TicketAuthor(
        String id,
        String avatar,
        String name,
        String role
) {}