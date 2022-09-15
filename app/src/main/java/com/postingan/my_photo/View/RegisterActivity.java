package com.postingan.my_photo.View;

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
import com.postingan.my_photo.Api.Response.ApiResponse;
import com.postingan.my_photo.Api.Response.RegisterResponse;
import com.postingan.my_photo.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiRequest = ApiConfig.getRetrofit(RegisterActivity.this).create(ApiRequest.class);

        binding.btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isError = false;

                if (binding.inputNameRegister.getText().length() == 0){
                    binding.inputNameRegister.setError("Field Still Empty");
                    isError = true;
                }
                if (binding.inputEmailRegister.getText().length() == 0){
                    binding.inputEmailRegister.setError("Field Still Empty");
                    isError = true;
                }
                if (binding.inputPassRegister.getText().length() == 0){
                    binding.inputPassRegister.setError("Field Still Empty");
                    isError = true;
                }
                if (binding.inputConPassRegister.getText().length() == 0){
                    binding.inputConPassRegister.setError("Field Still Empty");
                    isError = true;
                }
                if (!binding.inputPassRegister.getText().toString().equals(binding.inputConPassRegister.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Confirmation Password Not Valid", Toast.LENGTH_SHORT).show();
                    isError = true;
                }

                if (!isError){
                    apiRegister(
                            binding.inputNameRegister.getText().toString(),
                            binding.inputEmailRegister.getText().toString(),
                            binding.inputPassRegister.getText().toString(),
                            binding.inputConPassRegister.getText().toString()
                    );
                }
            }
        });
    }

    private void apiRegister(String name, String email, String pass, String conPass){
        Call<RegisterResponse> call = apiRequest.register(name, email, pass, conPass);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body() != null){
                    if (response.body().getData() != null){
                        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("register", t.toString());
                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}