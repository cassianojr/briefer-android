package br.com.briefer.briefer.config;

import br.com.briefer.briefer.services.BriefingService;
import br.com.briefer.briefer.services.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private static final String BASE_URL = "http://192.168.0.22:5000/api/";
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UserService getUserService(){
        return this.retrofit.create(UserService.class);
    }

    public BriefingService getBriefingService(){
        return this.retrofit.create(BriefingService.class);
    }
}
