package dev.astatic.nodestyclient.service;

import dev.astatic.nodestyclient.models.vps.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;
import java.util.List;

public interface VpsService {

    class VpsActionBody {
        private String action;
        public VpsActionBody(VpsAction action) {
            this.action = action.getValue();
        }
    }

    class VpsPasswordBody {
        private String password;
        public VpsPasswordBody(String password) {
            this.password = password;
        }
    }

    @POST("services/{id}/vps/action")
    Single<Void> performAction(@Path("id") String id, @Body VpsActionBody action);

    @POST("services/{id}/vps/backups/{date}/{file}")
    Single<Void> restoreBackup(@Path("id") String id, @Path("date") String date, @Path("file") String file, @Body VpsPasswordBody password);

    @POST("services/{id}/vps/password")
    Single<Void> changePassword(@Path("id") String id, @Body VpsChangePasswordData data);

    @GET("services/{id}/vps/graphs")
    Single<VpsGraphs> getGraphs(@Path("id") String id);

    @GET("services/{id}/vps/info")
    Single<VpsDetails> getDetails(@Path("id") String id);

    @GET("services/{id}/vps/os-templates")
    Single<List<VpsOsTemplate>> getOsTemplates(@Path("id") String id);

    @POST("services/{id}/vps/reinstall")
    Single<Void> reinstall(@Path("id") String id, @Body VpsReinstallData data);

    @GET("services/{id}/vps/tasks")
    Single<List<VpsTask>> getTasks(@Path("id") String id);
}