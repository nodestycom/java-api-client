package dev.astatic.nodestyclient.service;

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
     * Get nShield statistics for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return List of attack logs for the specified IP address
     */
    public CompletableFuture<ApiResponse<List<FirewallAttackLog>>> getAttackLogs(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/attack-logs", "GET", null, apiFetch.getTypeReferenceForList(FirewallAttackLog.class));
    }

    /**
     * Get attack notification settings for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return Attack notification settings for the specified service and IP address
     */
    public CompletableFuture<ApiResponse<AttackNotificationSettings>> getAttackNotificationSettings(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/attack-notification", "GET", null, AttackNotificationSettings.class);
    }

    /**
     * Update attack notification settings for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @param data New attack notification settings
     * @return Attack notification settings updated successfully
     */
    public CompletableFuture<ApiResponse<AttackNotificationSettings>> updateAttackNotificationSettings(String serviceId, String ip, AttackNotificationSettings data) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/attack-notification", "PUT", data, AttackNotificationSettings.class);
    }

    /**
     * Reset reverse DNS (rDNS) for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return Reverse DNS (rDNS) set successfully
     */
    public CompletableFuture<ApiResponse<Void>> resetReverseDns(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rdns", "DELETE", null, Void.class);
    }

    /**
     * Get reverse DNS (rDNS) for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return Reverse DNS (rDNS) for the specified IP address
     */
    public CompletableFuture<ApiResponse<FirewallReverseDns>> getReverseDns(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rdns", "GET", null, FirewallReverseDns.class);
    }

    /**
     * Set or update reverse DNS (rDNS) for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @param rdns Reverse DNS entry to set for the IP address
     * @return Reverse DNS (rDNS) set successfully
     */
    public CompletableFuture<ApiResponse<Void>> upsertReverseDns(String serviceId, String ip, String rdns) {
        // API body expects {"rdns": "example.domain.com"}, so we create a FirewallReverseDns object.
        FirewallReverseDns data = new FirewallReverseDns(rdns);
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rdns", "PUT", data, Void.class);
    }

    /**
     * Delete a nShield rule for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @param ruleId Rule ID
     * @return Rule deleted successfully
     */
    public CompletableFuture<ApiResponse<Void>> deleteRule(String serviceId, String ip, String ruleId) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rules/" + ruleId, "DELETE", null, Void.class);
    }

    /**
     * Get nShield rules for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return List of nShield rules for the specified service and IP address
     */
    public CompletableFuture<ApiResponse<List<FirewallRule>>> getRules(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rules", "GET", null, apiFetch.getTypeReferenceForList(FirewallRule.class));
    }

    /**
     * Create a new nShield rule for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @param data Rule data to create (port, appId)
     * @return Rule created successfully
     */
    public CompletableFuture<ApiResponse<Void>> createRule(String serviceId, String ip, FirewallCreateRuleData data) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/rules", "POST", data, Void.class);
    }

    /**
     * Get nShield statistics for a specific IP address on a VPS or Dedicated Server
     * @param serviceId Service ID
     * @param ip IP Address
     * @return nShield statistics for the specified IP address
     */
    public CompletableFuture<ApiResponse<List<FirewallStatistics>>> getStatistics(String serviceId, String ip) {
        return apiFetch.fetch("/services/" + serviceId + "/firewall/" + ip + "/stats", "GET", null, apiFetch.getTypeReferenceForList(FirewallStatistics.class));
    }
}