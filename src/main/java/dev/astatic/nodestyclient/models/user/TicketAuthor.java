package dev.astatic.nodestyclient.models.user;

public record TicketAuthor(
        String id,
        String avatar,
        String name,
        String role
) {}