package dev.astatic.nodestyclient.api.models.firewall;

public record AttackNotificationSettings(
        boolean emailNotification,
        String discordWebhookURL
) {}