package com.postingan.my_photo.Repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Api.Response.RegisterResponse;
import com.postingan.my_photo.Helper.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    ApiRequest apiRequest;
    Auth auth;
    MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    MutableLiveData<RegisterResponse> registerResponseMutableLiveData;

    public AuthRepository(Application application) {
        apiRequest = ApiConfig.getRetrofit(application).create(ApiRequest.class);
        auth = new Auth(application);
        loginResponseMutableLiveData = new MutableLiveData<>();
        registerResponseMutableLiveData = new MutableLiveData<>();
    }

    public void login(String email, String pass){
        Call<LoginResponse> call = apiRequest.login(email, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.body() != null){
                    loginResponseMutableLiveData.postValue(response.body());
                } else {
                    loginResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                Log.e("login", t.toString());
                loginResponseMutableLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<LoginResponse> getLogin(){
        return loginResponseMutableLiveData;
    }

    public void register(String name, String email, String pass, String conPass){
        Call<RegisterResponse> call = apiRequest.register(name, email, pass, conPass);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call,@NonNull Response<RegisterResponse> response) {
                if (response.body() != null){
                    registerResponseMutableLiveData.postValue(response.body());
                } else {
                    registerResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call,@NonNull Throwable t) {
                Log.e("register", t.toString());
                registerResponseMutableLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<RegisterResponse> getRegister(){
        return registerResponseMutableLiveData;
    }
}
