package com.example.cookpal2;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.Map;
import java.util.List;
import okhttp3.ResponseBody;

import com.example.cookpal2.models.*;


public interface UserApi {

    @GET("dashboard")
    Call<User> getDashboard();

    @GET("signup2")
    Call<Void> createNewUser2(@Query ("email") String email, @Query("password") String password, @Query("fullname") String fullname);

    @GET("login")
    Call<Map<String, String>> login(@Query("email") String email, @Query("password") String password);

    @GET("signup")
    Call<Map<String, Object>> createNewUser(@Query("email") String email, @Query("password") String password, @Query("fullname") String fullname);

}

