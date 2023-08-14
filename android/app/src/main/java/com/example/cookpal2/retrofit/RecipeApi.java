package com.example.cookpal2;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.Map;
import java.util.List;
import okhttp3.ResponseBody;

import com.example.cookpal2.models.*;


public interface MyApiServices {

    @GET("dashboard")
    Call<User> getDashboard();

    @GET("signup2")
    Call<Void> createNewUser2(@Query ("email") String email, @Query("password") String password, @Query("fullname") String fullname);

    @GET("login")
    Call<Map<String, String>> login(@Query("email") String email, @Query("password") String password);

    @GET("signup")
    Call<Map<String, Object>> createNewUser(@Query("email") String email, @Query("password") String password, @Query("fullname") String fullname);


    @GET("recipes")
    Call<List<Recipes>> getRecipes();

    @GET("recipes/id/{recipeId}")
    Call<Recipes> getRecipeById(@Path("recipeId") String recipeId);

    @GET("recipes/byname/{recipeName}")
    Call<List<Recipes>> getRecipeByName(@Path("recipeName") String recipeName);

    @GET("recipes/bypartial/{partialName}")
    Call<List<Recipes>> getRecipeByPartial(@Path("partialName") String partialName);

    @GET("recipes/category/{category}")
    Call<List<Recipes>> getRecipesByCategory(@Path("category") String category);

    @GET("recipes/rating/{rating}")
    Call<List<Recipes>> getRecipesByRating(@Path("rating") float rating);

    @POST("recipes")
    Call<Recipes> createRecipe(@Body Recipes recipe);

    @POST("recipes/pdf/{recipeId}")
    Call<String> saveRecipeAsPdf(@Path("recipeId") String recipeId);

    @GET("recipes/pdf/{recipeId}")
    @Streaming
    Call<ResponseBody> getRecipePdf(@Path("recipeId") String recipeId);

    @GET("recipes/total_time/{timeRange}")
    Call<List<Recipes>> getRecipesByTotalTime(@Path("timeRange") String timeRange);
}

