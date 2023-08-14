package com.example.cookpal2.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Path;

import com.example.cookpal2.models.User;

public interface MyApiServices {
    @GET("dashboard")
    Call<ResponseBody> getDashboard();

    @POST("user")
    Call<User> createUser(@Body User user);

    // Other API endpoints and their corresponding method declarations

    @GET("users/{userId}")
    Call<User> getId(@Path("userId") int userId);
}
