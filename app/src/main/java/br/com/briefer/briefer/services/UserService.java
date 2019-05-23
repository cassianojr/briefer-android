package br.com.briefer.briefer.services;

import br.com.briefer.briefer.model.JWTPayload;
import br.com.briefer.briefer.model.User;
import br.com.briefer.briefer.model.UserUpdate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/user/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @POST("users/login")
    Call<JWTPayload> loginUser(@Body User user);

    @PUT("users/update")
    Call<User> putUser(@Body UserUpdate user, @Header("Authorization") String jwtToken);
}
