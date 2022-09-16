package com.postingan.my_photo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Api.Response.RegisterResponse;
import com.postingan.my_photo.Repository.AuthRepository;

public class RegisterActivityViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;

    public RegisterActivityViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public void login(String email, String password){
        authRepository.login(email, password);
    }

    public MutableLiveData<LoginResponse> getLogin(){
        return authRepository.getLogin();
    }

    public void register(String name, String email, String password, String conPassword){
        authRepository.register(name, email, password, conPassword);
    }

    public MutableLiveData<RegisterResponse> getRegister(){
        return authRepository.getRegister();
    }
}
