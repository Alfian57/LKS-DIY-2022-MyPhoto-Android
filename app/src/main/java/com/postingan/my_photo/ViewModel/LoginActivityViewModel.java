package com.postingan.my_photo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Repository.AuthRepository;

public class LoginActivityViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public void login(String email, String password){
        authRepository.login(email, password);
    }

    public MutableLiveData<LoginResponse> getLogin(){
        return authRepository.getLogin();
    }
}
