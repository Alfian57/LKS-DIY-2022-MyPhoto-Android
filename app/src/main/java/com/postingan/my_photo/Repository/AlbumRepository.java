package com.postingan.my_photo.Repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.CreateAlbumResponse;
import com.postingan.my_photo.Api.Response.GetAlbumResponse;
import com.postingan.my_photo.Helper.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {
    ApiRequest apiRequest;
    Auth auth;
    MutableLiveData<GetAlbumResponse> getAlbumResponseMutableLiveData;
    MutableLiveData<CreateAlbumResponse> createAlbumResponseMutableLiveData;

    public AlbumRepository(Application application) {
        apiRequest = ApiConfig.getRetrofit(application).create(ApiRequest.class);
        auth = new Auth(application);
        getAlbumResponseMutableLiveData = new MutableLiveData<>();
        createAlbumResponseMutableLiveData = new MutableLiveData<>();
    }

    public void createAlbum(String name){
        Call<CreateAlbumResponse> call = apiRequest.createAlbum(auth.getToken(), name);
        call.enqueue(new Callback<CreateAlbumResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateAlbumResponse> call,@NonNull Response<CreateAlbumResponse> response) {
                if (response.body() != null){
                    createAlbumResponseMutableLiveData.postValue(response.body());
                } else {
                    createAlbumResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateAlbumResponse> call,@NonNull Throwable t) {
                Log.e("createAlbum", t.toString());
                createAlbumResponseMutableLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<CreateAlbumResponse> getCreateAlbum(){
        return createAlbumResponseMutableLiveData;
    }

    public MutableLiveData<GetAlbumResponse> getAlbum(){
        Call<GetAlbumResponse> call = apiRequest.getAlbum(auth.getToken());
        call.enqueue(new Callback<GetAlbumResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAlbumResponse> call, @NonNull Response<GetAlbumResponse> response) {
                if (response.body() != null){
                    getAlbumResponseMutableLiveData.postValue(response.body());
                } else {
                    getAlbumResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAlbumResponse> call,@NonNull Throwable t) {
                Log.e("getAlbum", t.toString());
                getAlbumResponseMutableLiveData.postValue(null);
            }
        });
        return getAlbumResponseMutableLiveData;
    }
}
