package dev.astatic.nodestyclient.service;

import com.google.gson.reflect.TypeToken;
import dev.astatic.nodestyclient.api.ApiFetchFunc;
import dev.astatic.nodestyclient.api.ApiResponse;
import dev.astatic.nodestyclient.api.models.firewall.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirewallApiService {
    private final ApiFetchFunc apiFetch;

    public FirewallApiService(ApiFetchFunc apiFetch) {
        this.apiFetch = apiFetch;
    }

    /**
     * Get nShield attack logs for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<List<FirewallAttackLog>>> getAttackLogs(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/attack-logs", id, ip), "GET", null, new TypeToken<List<FirewallAttackLog>>() {});
    }

    /**
     * Get attack notification settings for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<AttackNotificationSettings>> getAttackNotificationSettings(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/attack-notification", id, ip), "GET", null, AttackNotificationSettings.class);
    }

    /**
     * Update attack notification settings for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     * @param settings New attack notification settings
     */
    public CompletableFuture<ApiResponse<AttackNotificationSettings>> updateAttackNotificationSettings(String id, String ip, AttackNotificationSettings settings) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/attack-notification", id, ip), "PUT", settings, AttackNotificationSettings.class);
    }

    /**
     * Get block action history for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<List<FirewallHistoryEntry>>> getBlockHistory(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/block-history", id, ip), "GET", null, new TypeToken<List<FirewallHistoryEntry>>() {});
    }

    /**
     * Block or unblock an IP address
     * @param id Service ID
     * @param ip IP Address to block or unblock
     * @param action Action to perform (block or unblock)
     */
    public CompletableFuture<ApiResponse<Void>> blockIp(String id, String ip, FirewallBlockAction action) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/%s", id, ip, action.getValue()), "POST", null, Void.class);
    }

    /**
     * Get nShield reverse DNS for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<FirewallReverseDns>> getReverseDns(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/rdns", id, ip), "GET", null, FirewallReverseDns.class);
    }

    /**
     * Update nShield reverse DNS for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     * @param rdns Reverse DNS entry
     */
    public CompletableFuture<ApiResponse<FirewallReverseDns>> updateReverseDns(String id, String ip, String rdns) {
        record RdnsUpdateBody(String rdns) {}
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/rdns", id, ip), "PUT", new RdnsUpdateBody(rdns), FirewallReverseDns.class);
    }

    /**
     * Get nShield rules for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<List<FirewallRule>>> getRules(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/rules", id, ip), "GET", null, new TypeToken<List<FirewallRule>>() {});
    }

    /**
     * Create a new nShield rule for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     * @param data Rule data (port and app ID)
     */
    public CompletableFuture<ApiResponse<FirewallRule>> createRule(String id, String ip, FirewallCreateRuleData data) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/rules", id, ip), "POST", data, FirewallRule.class);
    }

    /**
     * Delete an existing nShield rule for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     * @param ruleId Rule ID to delete
     */
    public CompletableFuture<ApiResponse<Void>> deleteRule(String id, String ip, int ruleId) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/rules/%s", id, ip, ruleId), "DELETE", null, Void.class);
    }

    /**
     * Get nShield statistics for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<FirewallStatistics>> getStatistics(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/statistics", id, ip), "GET", null, FirewallStatistics.class);
    }

    /**
     * Get nShield white list for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip IP Address
     */
    public CompletableFuture<ApiResponse<List<FirewallWhiteList>>> getWhiteList(String id, String ip) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/whitelist", id, ip), "GET", null, new TypeToken<List<FirewallWhiteList>>() {});
    }

    /**
     * Add an IP address to the nShield white list for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip Target IP Address (service's IP)
     * @param ipAddress IP Address to whitelist
     */
    public CompletableFuture<ApiResponse<FirewallWhiteList>> addWhiteList(String id, String ip, String ipAddress) {
        record WhitelistAddBody(String ipAddress) {}
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/whitelist", id, ip), "POST", new WhitelistAddBody(ipAddress), FirewallWhiteList.class);
    }

    /**
     * Remove an IP address from the nShield white list for a specific IP address on a VPS or Dedicated Server
     * @param id Service ID
     * @param ip Target IP Address (service's IP)
     * @param whitelistId Whitelist entry ID to remove
     */
    public CompletableFuture<ApiResponse<Void>> removeWhiteList(String id, String ip, String whitelistId) {
        return apiFetch.fetch(String.format("/services/%s/firewall/%s/whitelist/%s", id, ip, whitelistId), "DELETE", null, Void.class);
    }
}