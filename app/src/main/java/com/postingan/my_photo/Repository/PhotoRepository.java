package com.postingan.my_photo.Repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.CreatePhotoResponse;
import com.postingan.my_photo.Api.Response.GetPhotoResponse;
import com.postingan.my_photo.Helper.Auth;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoRepository {
    ApiRequest apiRequest;
    Auth auth;
    MutableLiveData<GetPhotoResponse> getPhotoResponseMutableLiveData;
    MutableLiveData<GetPhotoResponse> getPhotoByAlbumMutableLiveData;
    MutableLiveData<CreatePhotoResponse> uploadPhotoResponseMutableLiveData;

    public PhotoRepository(Application application) {
        apiRequest = ApiConfig.getRetrofit(application).create(ApiRequest.class);
        auth = new Auth(application);
        getPhotoResponseMutableLiveData = new MutableLiveData<>();
        getPhotoByAlbumMutableLiveData = new MutableLiveData<>();
        uploadPhotoResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<GetPhotoResponse> getPhoto(){
        Call<GetPhotoResponse> call = apiRequest.getPhoto(auth.getToken());
        call.enqueue(new Callback<GetPhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPhotoResponse> call,@NonNull Response<GetPhotoResponse> response) {
                if (response.body() != null){
                    getPhotoResponseMutableLiveData.postValue(response.body());
                } else {
                    getPhotoResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPhotoResponse> call,@NonNull Throwable t) {
                Log.e("getPhoto", t.toString());
                getPhotoResponseMutableLiveData.postValue(null);
            }
        });

        return getPhotoResponseMutableLiveData;
    }

    public MutableLiveData<GetPhotoResponse> getPhotoByAlbum(int albumId){
        Call<GetPhotoResponse> call = apiRequest.getPhotoById(auth.getToken(), albumId);
        call.enqueue(new Callback<GetPhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPhotoResponse> call,@NonNull Response<GetPhotoResponse> response) {
                if (response.body() != null){
                    getPhotoByAlbumMutableLiveData.postValue(response.body());
                } else {
                    getPhotoByAlbumMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPhotoResponse> call,@NonNull Throwable t) {
                Log.e("getPhoto", t.toString());
                getPhotoByAlbumMutableLiveData.postValue(null);
            }
        });

        return getPhotoByAlbumMutableLiveData;
    }

    public void uploadImage(Integer albumId, MultipartBody.Part body) {
        Call<CreatePhotoResponse> call = apiRequest.createPhoto(auth.getToken(), albumId, body);
        call.enqueue(new Callback<CreatePhotoResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreatePhotoResponse> call,@NonNull Response<CreatePhotoResponse> response) {
                if (response.body() != null) {
                    uploadPhotoResponseMutableLiveData.postValue(response.body());
                } else {
                    uploadPhotoResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreatePhotoResponse> call,@NonNull Throwable t) {
                Log.e("createPhoto", t.toString());
                uploadPhotoResponseMutableLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<CreatePhotoResponse> getUploadImage(){
        return uploadPhotoResponseMutableLiveData;
    }
}
