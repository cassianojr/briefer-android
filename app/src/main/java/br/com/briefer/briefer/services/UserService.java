package br.com.briefer.briefer.services;

import br.com.briefer.briefer.model.JWTPayload;
import br.com.briefer.briefer.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/user/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @POST("users/login")
    Call<JWTPayload> loginUser(@Body User user);
}
