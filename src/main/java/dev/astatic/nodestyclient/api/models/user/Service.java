package dev.astatic.nodestyclient.api.models.user;

public record Service(
        String id,
        String name,
        String type,
        String status,
        String location,
        String ipAddress,
        long createdAt,
        long updatedAt,
        long nextDueDate
) {
}
