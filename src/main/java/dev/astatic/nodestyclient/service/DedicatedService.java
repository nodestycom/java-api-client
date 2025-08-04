package dev.astatic.nodestyclient.service;

import dev.astatic.nodestyclient.models.dedicated.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;
import java.util.List;

public interface DedicatedService {

    class DedicatedServerActionBody {
        private String action;

        public DedicatedServerActionBody(DedicatedServerAction action) {
            this.action = action.getValue();
        }
    }

    @POST("services/{id}/dedicated/action")
    Single<Void> performAction(@Path("id") String id, @Body DedicatedServerActionBody action);

    @GET("services/{id}/dedicated/hardware")
    Single<List<DedicatedServerHardwareComponent>> getHardwareComponents(@Path("id") String id);

    @GET("services/{id}/dedicated/info")
    Single<DedicatedServerDetails> getDetails(@Path("id") String id);

    @GET("services/{id}/dedicated/os-templates")
    Single<List<DedicatedServerOsTemplate>> getOsTemplates(@Path("id") String id);

    @GET("services/{id}/dedicated/reinstall-status")
    Single<DedicatedServerReinstallStatus> getReinstallStatus(@Path("id") String id);

    @POST("services/{id}/dedicated/reinstall")
    Single<Void> reinstall(@Path("id") String id, @Body DedicatedServerReinstallData data);

    @GET("services/{id}/dedicated/tasks")
    Single<List<DedicatedServerTask>> getTasks(@Path("id") String id);
}