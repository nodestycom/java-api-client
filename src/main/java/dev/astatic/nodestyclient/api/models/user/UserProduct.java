package dev.astatic.nodestyclient.api.models.user;

import java.util.List;

public record UserProduct(
        long id,
        long productId,
        long groupId,
        String name,
        String rawName,
        String domain,
        double firstPaymentAmount,
        double recurringAmount,
        String billingCycle,
        long nextDueDate,
        String status,
        String username,
        String password,
        Long vpsId,
        List<String> dedicatedId,
        boolean isVPS,
        boolean isWebHosting,
        boolean isDedicated,
        boolean isHetznerDedicated,
        boolean isSkyLinkDedicated,
        List<UserServiceAddon> addons,
        List<String> features
) {}