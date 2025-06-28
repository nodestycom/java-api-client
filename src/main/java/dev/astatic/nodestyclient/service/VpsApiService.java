package dev.astatic.nodestyclient.service;


import dev.astatic.nodestyclient.api.ApiFetchFunc;
import dev.astatic.nodestyclient.api.ApiResponse;
import dev.astatic.nodestyclient.api.models.vps.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class VpsApiService {
    private final ApiFetchFunc apiFetch;

    public VpsApiService(ApiFetchFunc apiFetch) {
        this.apiFetch = apiFetch;
    }

    /**
     * Perform an action on a VPS
     * @param id Service ID
     * @param action Action to perform
     * @return Action executed successfully
     */
    public CompletableFuture<ApiResponse<Void>> performAction(String id, VpsAction action) {
        return apiFetch.fetch("/services/" + id + "/vps/action", "POST",
                java.util.Collections.singletonMap("action", action.getValue()), Void.class);
    }

    /**
     * Restore a VPS backup
     * @param id Service ID
     * @param date Date of the backup
     * @param file Name of the backup file
     * @return VPS backup restore initiated successfully
     */
    public CompletableFuture<ApiResponse<Void>> restoreBackup(String id, String date, String file) {
        return apiFetch.fetch("/services/" + id + "/vps/backups/" + date + "/" + file, "POST", null, Void.class);
    }

    /**
     * Get VPS backups
     * @param id Service ID
     * @return List of VPS backups
     */
    public CompletableFuture<ApiResponse<List<VpsBackup>>> getBackups(String id) {
        return apiFetch.fetch("/services/" + id + "/vps/backups", "GET", null,
                apiFetch.getTypeReferenceForList(VpsBackup.class));
    }

    /**
     * Change the password of a VPS
     * @param id Service ID
     * @param data Change password data (username, new password)
     * @return Password changed successfully
     */
    public CompletableFuture<ApiResponse<Void>> changePassword(String id, VpsChangePasswordData data) {
        return apiFetch.fetch("/services/" + id + "/vps/change-password", "POST", data, Void.class);
    }

    /**
     * Get VPS usage statistics graphs
     * @param id Service ID
     * @return VPS usage statistics graphs
     */
    public CompletableFuture<ApiResponse<VpsGraphs>> getGraphs(String id) {
        return apiFetch.fetch("/services/" + id + "/vps/graphs", "GET", null, VpsGraphs.class);
    }

    /**
     * Get VPS information
     * @param id Service ID
     * @return Information about the VPS
     */
    public CompletableFuture<ApiResponse<VpsDetails>> getDetails(String id) {
        return apiFetch.fetch("/services/" + id + "/vps/info", "GET", null, VpsDetails.class);
    }

    /**
     * Get available OS templates for a VPS
     * @param id Service ID
     * @return List of available OS templates
     */
    public CompletableFuture<ApiResponse<List<VpsOsTemplate>>> getOsTemplates(String id) {
        return apiFetch.fetch("/services/" + id + "/vps/os-templates", "GET", null,
                apiFetch.getTypeReferenceForList(VpsOsTemplate.class));
    }

    /**
     * Reinstall a VPS with a new OS
     * @param id Service ID
     * @param data Reinstall data (password, osId)
     * @return VPS reinstallation initiated successfully
     */
    public CompletableFuture<ApiResponse<Void>> reinstall(String id, VpsReinstallData data) {
        return apiFetch.fetch("/services/" + id + "/vps/reinstall", "POST", data, Void.class);
    }

    /**
     * Get VPS tasks
     * @param id Service ID
     * @return List of VPS tasks
     */
    public CompletableFuture<ApiResponse<List<VpsTask>>> getTasks(String id) {
        return apiFetch.fetch("/services/" + id + "/vps/tasks", "GET", null,
                apiFetch.getTypeReferenceForList(VpsTask.class));
    }
}