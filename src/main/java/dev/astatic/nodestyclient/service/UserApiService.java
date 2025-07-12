package dev.astatic.nodestyclient.service;

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
     * Get the current user services.
     * @return List of services for the current user.
     */
    public CompletableFuture<ApiResponse<List<UserProduct>>> getServices() {
        return apiFetch.fetch("/services", "GET", null,
                apiFetch.getTypeReferenceForList(UserProduct.class));
    }

    /**
     * Get the current user ticket by ID.
     * @param id Ticket ID.
     * @return Ticket details for the specified ID.
     */
    public CompletableFuture<ApiResponse<UserTicketDetails>> getTicket(String id) {
        return apiFetch.fetch("/tickets/" + id, "GET", null, UserTicketDetails.class);
    }

    /**
     * Get the current user tickets.
     * @return List of tickets for the current user.
     */
    public CompletableFuture<ApiResponse<List<UserTicketSummary>>> getTickets() {
        return apiFetch.fetch("/tickets", "GET", null,
                apiFetch.getTypeReferenceForList(UserTicketSummary.class));
    }

    /**
     * Get the current user information.
     * @return Current user information.
     */
    public CompletableFuture<ApiResponse<CurrentUser>> getCurrentUser() {
        return apiFetch.fetch("/users/@me", "GET", null, CurrentUser.class);
    }

    /**
     * Get the current user invoice by ID.
     * @param id Invoice ID.
     * @return Invoice details for the specified ID.
     */
    public CompletableFuture<ApiResponse<UserInvoiceDetails>> getInvoice(String id) {
        return apiFetch.fetch("/users/@me/invoices/" + id, "GET", null, UserInvoiceDetails.class);
    }

    /**
     * Get the current user invoices.
     * @return List of invoices for current user.
     */
    public CompletableFuture<ApiResponse<List<UserInvoiceSummary>>> getInvoices() {
        return apiFetch.fetch("/users/@me/invoices", "GET", null,
                apiFetch.getTypeReferenceForList(UserInvoiceSummary.class));
    }

    /**
     * Get the current user sessions.
     * @return List of user sessions.
     */
    public CompletableFuture<ApiResponse<List<UserSession>>> getSessions() {
        return apiFetch.fetch("/users/@me/sessions", "GET", null,
                apiFetch.getTypeReferenceForList(UserSession.class));
    }
}