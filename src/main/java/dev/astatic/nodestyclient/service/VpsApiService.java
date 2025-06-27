package dev.astatic.nodestyclient.service;


import com.google.gson.reflect.TypeToken;
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
     */
    public CompletableFuture<ApiResponse<Void>> performAction(String id, VpsAction action) {
        return apiFetch.fetch(String.format("/services/%s/vps/action", id), "POST", new ActionBody(action.getValue()), Void.class);
    }

    private record ActionBody(String action) {}

    /**
     * Restore a VPS backup
     * @param id Service ID
     * @param data Data for restoring backup (date and file)
     */
    public CompletableFuture<ApiResponse<Void>> restoreBackup(String id, VpsBackup data) {
        return apiFetch.fetch(String.format("/services/%s/vps/backups/%s/%s", id, data.date(), data.file()), "POST", null, Void.class);
    }

    /**
     * Get VPS backups
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<VpsBackup>>> getBackups(String id) {
        return apiFetch.fetch(String.format("/services/%s/vps/backups", id), "GET", null, new TypeToken<List<VpsBackup>>() {});
    }

    /**
     * Change the password of a VPS
     * @param id Service ID
     * @param data New password data (username and password)
     */
    public CompletableFuture<ApiResponse<Void>> changePassword(String id, VpsChangePasswordData data) {
        return apiFetch.fetch(String.format("/services/%s/vps/password", id), "PUT", data, Void.class);
    }

    /**
     * Get VPS details
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<VpsDetails>> getDetails(String id) {
        return apiFetch.fetch(String.format("/services/%s/vps/info", id), "GET", null, VpsDetails.class);
    }

    /**
     * Get available OS templates for a VPS
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<VpsOsTemplate>>> getOsTemplates(String id) {
        return apiFetch.fetch(String.format("/services/%s/vps/os-templates", id), "GET", null, new TypeToken<List<VpsOsTemplate>>() {});
    }

    /**
     * Reinstall a VPS with a new OS
     * @param id Service ID
     * @param data Reinstall data (password and OS ID)
     */
    public CompletableFuture<ApiResponse<Void>> reinstall(String id, VpsReinstallData data) {
        return apiFetch.fetch(String.format("/services/%s/vps/reinstall", id), "POST", data, Void.class);
    }

    /**
     * Get VPS tasks
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<VpsTask>>> getTasks(String id) {
        return apiFetch.fetch(String.format("/services/%s/vps/tasks", id), "GET", null, new TypeToken<List<VpsTask>>() {});
    }
}