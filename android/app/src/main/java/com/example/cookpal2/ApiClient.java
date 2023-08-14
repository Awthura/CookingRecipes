package com.example.cookpal2;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.ResponseBody;
import com.example.cookpal2.models.User;
import com.example.cookpal2.ApiServiceGenerator;
import com.example.cookpal2.retrofit.MyApiServices;


import java.io.IOException;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void handleApiResponse(Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    String json = responseBody.string();
                    Gson gson = new Gson();
                    User user = gson.fromJson(json, User.class);
                    // Do something with the mapped user object
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Handle API error
        }
    }


    // Example API call
    public void getUser() {
        MyApiServices apiService = ApiClient.getClient().create(MyApiServices.class);
        Call<ResponseBody> call = apiService.getDashboard();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handleApiResponse(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle API failure
            }
        });
    }

}
