package dev.astatic.nodestyclient.service;

import dev.astatic.nodestyclient.models.firewall.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import java.util.List;

public interface FirewallService {

    class FirewallPortBody {
        private int port;
        public FirewallPortBody(int port) {
            this.port = port;
        }
    }

    class FirewallAppIdBody {
        private String appId;
        public FirewallAppIdBody(String appId) {
            this.appId = appId;
        }
    }

    @GET("services/{serviceId}/firewall/{ip}/attack-logs")
    Single<List<FirewallAttackLog>> getAttackLogs(@Path("serviceId") String serviceId, @Path("ip") String ip);

    @GET("services/{serviceId}/firewall/{ip}/attack-notification")
    Single<AttackNotificationSettings> getAttackNotificationSettings(@Path("serviceId") String serviceId, @Path("ip") String ip);

    @PUT("services/{serviceId}/firewall/{ip}/attack-notification")
    Single<Void> updateAttackNotificationSettings(@Path("serviceId") String serviceId, @Path("ip") String ip, @Body AttackNotificationSettings data);

    @GET("services/{serviceId}/firewall/{ip}/reverse-dns")
    Single<List<FirewallReverseDns>> getReverseDns(@Path("serviceId") String serviceId, @Path("ip") String ip);

    @PUT("services/{serviceId}/firewall/{ip}/reverse-dns")
    Single<Void> updateReverseDns(@Path("serviceId") String serviceId, @Path("ip") String ip, @Body FirewallReverseDns data);

    @GET("services/{serviceId}/firewall/{ip}/rules")
    Single<List<FirewallRule>> getRules(@Path("serviceId") String serviceId, @Path("ip") String ip);

    @POST("services/{serviceId}/firewall/{ip}/rules")
    Single<Void> createRule(@Path("serviceId") String serviceId, @Path("ip") String ip, @Body FirewallCreateRuleData data);

    @HTTP(method = "DELETE", path = "services/{serviceId}/firewall/{ip}/rules/port", hasBody = true)
    Single<Void> deleteRuleByPort(@Path("serviceId") String serviceId, @Path("ip") String ip, @Body FirewallPortBody port);

    @HTTP(method = "DELETE", path = "services/{serviceId}/firewall/{ip}/rules/app", hasBody = true)
    Single<Void> deleteRuleByAppId(@Path("serviceId") String serviceId, @Path("ip") String ip, @Body FirewallAppIdBody appId);

    @GET("services/{serviceId}/firewall/{ip}/stats")
    Single<List<FirewallStatistics>> getStatistics(@Path("serviceId") String serviceId, @Path("ip") String ip);
}