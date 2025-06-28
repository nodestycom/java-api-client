package dev.astatic.nodestyclient.api.models.user;

public record CurrentUser(
        String id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String country,
        String city,
        String state,
        String address,
        String postCode,
        String currency,
        String currencySymbol,
        String phoneNumber,
        String tckn,
        String birthYear,
        boolean banned,
        String currentSessionId,
        boolean totpEnabled,
        UserStats stats,
        String companyName
) {}