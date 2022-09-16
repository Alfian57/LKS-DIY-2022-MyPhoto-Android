package com.postingan.my_photo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.postingan.my_photo.Api.Response.LoginResponse;
import com.postingan.my_photo.Api.Response.RegisterResponse;
import com.postingan.my_photo.Helper.Auth;
import com.postingan.my_photo.ViewModel.RegisterActivityViewModel;
import com.postingan.my_photo.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    RegisterActivityViewModel registerActivityViewModel;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerActivityViewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);
        auth = new Auth(RegisterActivity.this);

        registerActivityViewModel.getRegister().observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse != null){
                    if (registerResponse.getData() != null){
                        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerActivityViewModel.getLogin().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null){
                    if (loginResponse.getData() != null){
                        if (loginResponse.getData().getSignature() != null){
                            auth.setToken(loginResponse.getData().getSignature());
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEditText()){
                    registerActivityViewModel.login(
                            binding.inputEmailRegister.getText().toString(),
                            binding.inputPassRegister.getText().toString()
                    );
                }
            }
        });

        binding.btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEditText()){
                    registerActivityViewModel.register(
                            binding.inputNameRegister.getText().toString(),
                            binding.inputEmailRegister.getText().toString(),
                            binding.inputPassRegister.getText().toString(),
                            binding.inputConPassRegister.getText().toString()
                    );
                }
            }
        });
    }

    private boolean validateEditText(){
        boolean isSuccess = true;

        if (binding.inputNameRegister.getText().length() == 0){
            binding.inputNameRegister.setError("Field Still Empty");
            isSuccess = false;
        }
        if (binding.inputEmailRegister.getText().length() == 0){
            binding.inputEmailRegister.setError("Field Still Empty");
            isSuccess = false;
        }
        if (binding.inputPassRegister.getText().length() == 0){
            binding.inputPassRegister.setError("Field Still Empty");
            isSuccess = false;
        }
        if (binding.inputConPassRegister.getText().length() == 0){
            binding.inputConPassRegister.setError("Field Still Empty");
            isSuccess = false;
        }
        if (!binding.inputPassRegister.getText().toString().equals(binding.inputConPassRegister.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Confirmation Password Not Valid", Toast.LENGTH_SHORT).show();
            isSuccess = false;
        }

        return isSuccess;
    }
}