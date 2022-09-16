package com.postingan.my_photo.Repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.GetUserResponse;
import com.postingan.my_photo.Helper.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    ApiRequest apiRequest;
    Auth auth;
    MutableLiveData<GetUserResponse> getUserResponseMutableLiveData;

    public UserRepository(Application application) {
        apiRequest = ApiConfig.getRetrofit(application).create(ApiRequest.class);
        auth = new Auth(application);
        getUserResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<GetUserResponse> userDetail(){
        Call<GetUserResponse> call = apiRequest.me(auth.getToken());
        call.enqueue(new Callback<GetUserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call,@NonNull Response<GetUserResponse> response) {
                if (response.body() != null){
                    getUserResponseMutableLiveData.postValue(response.body());
                } else {
                    getUserResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call,@NonNull Throwable t) {
                Log.e("getName", t.toString());
                getUserResponseMutableLiveData.postValue(null);
            }
        });
        return getUserResponseMutableLiveData;
    }
}
