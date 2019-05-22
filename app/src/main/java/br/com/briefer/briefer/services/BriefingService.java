package br.com.briefer.briefer.services;

import java.util.List;

import br.com.briefer.briefer.model.Briefing;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BriefingService {

    /**
     * Get a list of briefings related to user.
     * @param token the JWT Token used to identify the user.
     * @return List of briefings.
     */
    @GET("briefings")
    Call<List<Briefing>> getBriefings(@Header("Authorization") String token);

    /**
     * Make a POST request on the webservice.
     * @param briefing The briefing that will be created.
     * @param token The JWT Token used to identify the user.
     * @return Briefing The Generated Briefing.
     */
    @POST("briefings")
    Call<Briefing> postBriefing(@Body Briefing briefing, @Header("Authorization") String token);

    /**
     * Make a PUT request on the webservice.
     * @param briefing The briefing that will be updated.
     * @param token the JWT Token used to identify the user.
     * @return Briefing The Updated briefing.
     */
    @PUT("briefings/update")
    Call<Briefing> putBriefing(@Body Briefing briefing, @Header("Authorization") String token);

    /**
     * Make a DELETE request on the webservice.
     * @param briefing The birefing that will be deleted.
     * @param token The JWT Token used to identify the user.
     * @return Briefing The deleted briefing.
     */
    @HTTP(method = "DELETE", path = "briefings", hasBody = true)
    Call<Briefing> deleteBriefing(@Body Briefing briefing, @Header("Authorization") String token);

    /**
     * Make a GET request on the webservice for a sigle briefing.
     * @param id The Briefing Unique ID.
     * @param token The JWT Token used to identify the user.
     * @return Briefing The Briefing finded.
     */
    @GET("briefings/briefing/{id}")
    Call<Briefing> getSingleBriefing(@Path("id") String id, @Header("Authorization") String token);
}
