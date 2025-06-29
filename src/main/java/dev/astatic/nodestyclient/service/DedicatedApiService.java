package dev.astatic.nodestyclient.service;

import com.google.gson.reflect.TypeToken;
import dev.astatic.nodestyclient.api.ApiFetchFunc;
import dev.astatic.nodestyclient.api.ApiResponse;
import dev.astatic.nodestyclient.api.models.dedicated.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class DedicatedApiService {
    private final ApiFetchFunc apiFetch;

    public DedicatedApiService(ApiFetchFunc apiFetch) {
        this.apiFetch = apiFetch;
    }

    /**
     * Perform an action on a dedicated server
     * @param id Service ID
     * @param action Action to perform
     * @return Action executed successfully
     */
    public CompletableFuture<ApiResponse<Void>> performAction(String id, DedicatedServerAction action) {
        return apiFetch.fetch("/services/" + id + "/dedicated/action", "POST",
                Collections.singletonMap("action", action.getValue()), Void.class);
    }

    /**
     * Retrieve hardware components of a dedicated server
     * @param id Service ID
     * @return List of hardware components for the dedicated server
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerHardwareComponent>>> getHardwareComponents(String id) {
        return apiFetch.fetch("/services/" + id + "/dedicated/hardware", "GET", null,
                apiFetch.getTypeReferenceForList(DedicatedServerHardwareComponent.class));
    }

    /**
     * Get dedicated server information
     * @param id Service ID
     * @return Information about the dedicated server
     */
    public CompletableFuture<ApiResponse<DedicatedServerDetails>> getDetails(String id) {
        return apiFetch.fetch("/services/" + id + "/dedicated/info", "GET", null, DedicatedServerDetails.class);
    }

    /**
     * Get available OS templates for a dedicated server
     * @param id Service ID
     * @return List of available OS templates
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerOsTemplate>>> getOsTemplates(String id) {
        return apiFetch.fetch("/services/" + id + "/dedicated/os-templates", "GET", null,
                apiFetch.getTypeReferenceForList(DedicatedServerOsTemplate.class));
    }

    /**
     * Get dedicated server reinstall status
     * @param id Service ID
     * @return Dedicated server reinstall status
     */
    public CompletableFuture<ApiResponse<DedicatedServerReinstallStatus>> getReinstallStatus(String id) {
        return apiFetch.fetch("/services/" + id + "/dedicated/reinstall-status", "GET", null, DedicatedServerReinstallStatus.class);
    }

    /**
     * Reinstall a dedicated server with a new OS
     * @param id Service ID
     * @param data Reinstall data (password, osId)
     * @return VPS reinstallation initiated successfully
     */
    public CompletableFuture<ApiResponse<Void>> reinstall(String id, DedicatedServerReinstallData data) {
        return apiFetch.fetch("/services/" + id + "/dedicated/reinstall", "POST", data, Void.class);
    }

    /**
     * Get dedicated server tasks
     * @param id Service ID
     * @return List of dedicated server tasks
     */
    public CompletableFuture<ApiResponse<List<DedicatedServerTask>>> getTasks(String id) {
        return apiFetch.fetch("/services/" + id + "/dedicated/tasks", "GET", null,
                apiFetch.getTypeReferenceForList(DedicatedServerTask.class));
    }
}