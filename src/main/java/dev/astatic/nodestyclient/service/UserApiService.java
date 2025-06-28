package dev.astatic.nodestyclient.service;


import com.google.gson.reflect.TypeToken;
import dev.astatic.nodestyclient.api.ApiFetchFunc;
import dev.astatic.nodestyclient.api.ApiResponse;
import dev.astatic.nodestyclient.api.models.user.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserApiService {
    private final ApiFetchFunc apiFetch;

    public UserApiService(ApiFetchFunc apiFetch) {
        this.apiFetch = apiFetch;
    }

    /**
     * Get the current user services
     */
    public CompletableFuture<ApiResponse<List<Service>>> getServices() {
        return apiFetch.fetch("/services", "GET", null, new TypeToken<List<Service>>() {
        });
    }

    /**
     * Get the current user ticket by ID
     *
     * @param ticketId Ticket ID
     */
    public CompletableFuture<ApiResponse<Ticket>> getTicketById(String ticketId) {
        return apiFetch.fetch(String.format("/tickets/%s", ticketId), "GET", null, Ticket.class);
    }

    /**
     * Get the current user tickets
     */
    public CompletableFuture<ApiResponse<List<Ticket>>> getTickets() {
        return apiFetch.fetch("/tickets", "GET", null, new TypeToken<List<Ticket>>() {
        });
    }

    /**
     * Get the current user information
     */
    public CompletableFuture<ApiResponse<User>> getCurrentUser() {
        return apiFetch.fetch("/users/@me", "GET", null, User.class);
    }

    /**
     * Get the current user invoice by ID
     *
     * @param invoiceId Invoice ID
     */
    public CompletableFuture<ApiResponse<Invoice>> getInvoiceById(String invoiceId) {
        return apiFetch.fetch(String.format("/users/@me/invoices/%s", invoiceId), "GET", null, Invoice.class);
    }

    /**
     * Get the current user invoices
     */
    public CompletableFuture<ApiResponse<List<Invoice>>> getInvoices() {
        return apiFetch.fetch("/users/@me/invoices", "GET", null, new TypeToken<List<Invoice>>() {
        });
    }

    /**
     * Get the current user sessions
     */
    public CompletableFuture<ApiResponse<List<Session>>> getSessions() {
        return apiFetch.fetch("/users/@me/sessions", "GET", null, new TypeToken<List<Session>>() {
        });
    }

    /**
     * Delete a session
     *
     * @param sessionId Session ID
     */
    public CompletableFuture<ApiResponse<Void>> deleteSession(String sessionId) {
        return apiFetch.fetch(String.format("/users/@me/sessions/%s", sessionId), "DELETE", null, Void.class);
    }
}