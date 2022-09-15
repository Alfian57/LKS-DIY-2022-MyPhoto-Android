package com.postingan.my_photo.Api;

import com.postingan.my_photo.Api.Response.CreateAlbumResponse;
import com.postingan.my_photo.Api.Response.CreatePhotoResponse;
import com.postingan.my_photo.Api.Response.GetAlbumResponse;
import com.postingan.my_photo.Api.Response.ApiResponse;
import com.postingan.my_photo.Api.Response.GetUserResponse;
import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Api.Response.GetPhotoResponse;
import com.postingan.my_photo.Api.Response.RegisterResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field(" password_confirmation") String ConPassword
    );

    @GET("user/album")
    Call<GetAlbumResponse> getAlbum(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("album")
    Call<CreateAlbumResponse> createAlbum(
            @Header("Authorization") String token,
            @Field("name") String name
    );

    @GET("user/photo")
    Call<GetPhotoResponse> getPhoto(
            @Header("Authorization") String token
    );

    @GET("user/photo")
    Call<GetPhotoResponse> getPhotoById(
            @Header("Authorization") String token,
            @Query("album_id") int albumId
    );

    @Multipart
    @POST("photo")
    Call<CreatePhotoResponse> createPhoto(
            @Header("Authorization") String token,
            @Part("album_id") Integer id,
            @Part MultipartBody.Part image
    );

    @GET("user")
    Call<GetUserResponse> me(
            @Header("Authorization") String token
    );
}
