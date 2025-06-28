package dev.astatic.nodestyclient.api.models.user;

public record User(
        String id,
        String firstName,
        String lastName,
        String email,
        String company,
        String address1,
        String address2,
        String city,
        String state,
        String postCode,
        String country,
        String phoneNumber,
        String locale,
        boolean emailVerified,
        boolean hasTwoFactor,
        long createdAt,
        long updatedAt
) {
}