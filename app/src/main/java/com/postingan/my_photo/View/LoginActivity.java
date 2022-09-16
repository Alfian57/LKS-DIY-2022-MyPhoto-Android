package com.postingan.my_photo.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Helper.Auth;
import com.postingan.my_photo.ViewModel.LoginActivityViewModel;
import com.postingan.my_photo.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    LoginActivityViewModel loginActivityViewModel;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        auth = new Auth(LoginActivity.this);

        loginActivityViewModel.getLogin().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null){
                    if (loginResponse.getData() != null){
                        if (loginResponse.getData().getSignature() != null){
                            auth.setToken(loginResponse.getData().getSignature());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.btnSubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isError = false;

                if (binding.inputEmailLogin.getText().length() == 0){
                    binding.inputEmailLogin.setError("Field Still Empty");
                    isError = true;
                }
                if (binding.inputPassLogin.getText().length() == 0){
                    binding.inputPassLogin.setError("Field Still Empty");
                    isError = true;
                }

                if (!isError){
                    loginActivityViewModel.login(
                            binding.inputEmailLogin.getText().toString(),
                            binding.inputPassLogin.getText().toString());
                }
            }
        });

    }

}