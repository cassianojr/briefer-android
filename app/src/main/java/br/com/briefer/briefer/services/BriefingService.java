package br.com.briefer.briefer.services;

import java.util.List;

import br.com.briefer.briefer.model.Briefing;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BriefingService {

    @GET("briefings")
    Call<List<Briefing>> getBriefings(@Header("Authorization") String token);

    @POST("briefings")
    Call<Briefing> postBriefing(@Body Briefing briefing, @Header("Authorization") String token);

    @PUT("briefings/update")
    Call<Briefing> putBriefing(@Body Briefing briefing, @Header("Authorization") String token);

    @DELETE("briefings")
    Call<Briefing> deleteBriefing(@Body int id, @Header("Authorization") String token);

    @GET("briefings/briefing/{id}")
    Call<Briefing> getSingleBriefing(@Path("id") int id, @Header("Authorization") String token);
}
