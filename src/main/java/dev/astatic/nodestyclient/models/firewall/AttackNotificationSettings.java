package dev.astatic.nodestyclient.models.firewall;

public record AttackNotificationSettings(
        boolean emailNotification,
        String discordWebhookURL) {}