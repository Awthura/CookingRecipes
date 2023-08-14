package com.example.cookpal2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.cookpal2.retrofit.MyApiServices;

public class ApiServiceGenerator {
    private static final String BASE_URL = "http://localhost:8080";

    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static MyApiServices createApiService() {
        return getRetrofitInstance().create(MyApiServices.class);
    }
}
