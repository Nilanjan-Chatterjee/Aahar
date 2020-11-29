package com.example.aahar;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {
    @FormUrlEncoded
    @POST("authentication.php")
    Call<SignupResponse> createUser(
            @Field("op") Integer op,
            @Field("number") String email,
            @Field("password") String password,
            @Field("name") String name
    );
    @FormUrlEncoded
    @POST("authentication.php")
    Call<SignupResponse> loginUser(
            @Field("op") Integer op,
            @Field("number") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("authentication.php")
    Call<foodresponse> FetchFoodDetails(
            @Field("op") Integer op,
            @Field("place") String place,
            @Field("cuisine") String cuisine
    );
    @FormUrlEncoded
    @POST("authentication.php")
    Call<foodresponse> RestaurantDetails(
            @Field("op") Integer op,
            @Field("restaurant_id") int restaurant_id
    );
    @FormUrlEncoded
    @POST("authentication.php")
    Call<SignupResponse> placeOrder(
            @FieldMap Map<String, String> fields);

}
