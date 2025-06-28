package dev.astatic.nodestyclient.service;

import com.google.gson.reflect.TypeToken;
import dev.astatic.nodestyclient.api.ApiFetchFunc;
import dev.astatic.nodestyclient.api.ApiResponse;
import dev.astatic.nodestyclient.api.models.dedicated.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DedicatedApiService {
    private final ApiFetchFunc apiFetch;

    public DedicatedApiService(ApiFetchFunc apiFetch) {
        this.apiFetch = apiFetch;
    }

    /**
     * Perform an action on a dedicated server
     *
     * @param id     Service ID
     * @param action Action to perform
     */
    public CompletableFuture<ApiResponse<Void>> performAction(String id, DedicatedServerAction action) {
        record ActionBody(String action) {
        }
        return apiFetch.fetch(String.format("/services/%s/dedicated/action", id), "POST", new ActionBody(action.getValue()), Void.class);
    }

    /**
     * Get hardware components of a dedicated server
     *
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerHardwareComponent>>> getHardwareComponents(String id) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/hardware", id), "GET", null, new TypeToken<>() {
        });
    }

    /**
     * Get dedicated server details
     *
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<DedicatedServerDetails>> getDetails(String id) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/info", id), "GET", null, DedicatedServerDetails.class);
    }

    /**
     * Get available OS templates for a dedicated server
     *
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerOsTemplate>>> getOsTemplates(String id) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/os-templates", id), "GET", null, new TypeToken<>() {
        });
    }

    /**
     * Get the reinstall status of a dedicated server
     *
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<DedicatedServerReinstallStatus>> getReinstallStatus(String id) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/reinstall-status", id), "GET", null, DedicatedServerReinstallStatus.class);
    }

    /**
     * Reinstall a dedicated server with a new OS
     *
     * @param id   Service ID
     * @param data Reinstall data (password, OS ID, and send email flag)
     */
    public CompletableFuture<ApiResponse<Void>> reinstall(String id, DedicatedServerReinstallData data) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/reinstall", id), "POST", data, Void.class);
    }

    /**
     * Get tasks for a dedicated server
     *
     * @param id Service ID
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerTask>>> getTasks(String id) {
        return apiFetch.fetch(String.format("/services/%s/dedicated/tasks", id), "GET", null, new TypeToken<>() {
        });
    }
}