package dev.astatic.nodestyclient.service;

import dev.astatic.nodestyclient.models.user.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

public interface UserService {
    @GET("services")
    Single<List<UserProduct>> getServices();

    @GET("tickets/{id}")
    Single<UserTicketDetails> getTicket(@Path("id") String id);

    @GET("tickets")
    Single<List<UserTicketSummary>> getTickets();

    @GET("users/@me")
    Single<CurrentUser> getCurrentUser();

    @GET("users/@me/invoices/{id}")
    Single<UserInvoiceDetails> getInvoice(@Path("id") String id);

    @GET("users/@me/invoices")
    Single<List<UserInvoiceSummary>> getInvoices();

    @GET("users/@me/sessions")
    Single<List<UserSession>> getSessions();
}