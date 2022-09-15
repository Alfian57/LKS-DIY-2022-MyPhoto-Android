package com.postingan.my_photo.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.postingan.my_photo.Api.ApiConfig;
import com.postingan.my_photo.Api.ApiRequest;
import com.postingan.my_photo.Api.Response.ApiResponse;
import com.postingan.my_photo.Api.Response.CreatePhotoResponse;
import com.postingan.my_photo.Api.Response.GetUserResponse;
import com.postingan.my_photo.Helper.Auth;
import com.postingan.my_photo.databinding.ActivityUploadPhotoBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoActivity extends AppCompatActivity {
    ActivityUploadPhotoBinding binding;
    ApiRequest apiRequest;
    Auth auth;
    Uri imagePath;
    int albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                albumId = 0;
            } else {
                albumId = (int) extras.getSerializable("albumId");
            }
        } else {
            albumId = (int) savedInstanceState.getSerializable("albumId");
        }

        apiRequest = ApiConfig.getRetrofit(UploadPhotoActivity.this).create(ApiRequest.class);
        auth = new Auth(UploadPhotoActivity.this);

        apiGetName();

        binding.btnBackUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UploadPhotoActivity.this, DetailActivity.class);
                i.putExtra("albumId", albumId);
                startActivity(i);
            }
        });

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UploadPhotoActivity.this)
                        .crop()
                        .start();
            }
        });
    }

    public void apiGetName(){
        Call<GetUserResponse> call = apiRequest.me(auth.getToken());
        call.enqueue(new Callback<GetUserResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.body() != null){
                    if (response.body().getName() != null){
                        binding.txtHelloUpload.setText("Hello, " + response.body().getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                Log.e("getName", t.toString());
                Toast.makeText(UploadPhotoActivity.this, "Failed To Get User Name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            imagePath = data.getData();
            apiUploadImage();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void apiUploadImage(){
        if (getRealPathFromURI(imagePath) != null) {
            File file = new File(getRealPathFromURI(imagePath)); //Get File
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

            Call<CreatePhotoResponse> call = apiRequest.createPhoto(auth.getToken(), albumId, body);
            call.enqueue(new Callback<CreatePhotoResponse>() {
                @Override
                public void onResponse(Call<CreatePhotoResponse> call, Response<CreatePhotoResponse> response) {
                    if (response.body() != null){
                        if (response.body().getData() != null){
                            Intent i = new Intent(UploadPhotoActivity.this, DetailActivity.class);
                            i.putExtra("albumId", albumId);
                            startActivity(i);
                            Toast.makeText(UploadPhotoActivity.this, "Success To Upload Photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CreatePhotoResponse> call, Throwable t) {
                    Log.e("createPhoto", t.toString());
                    Toast.makeText(UploadPhotoActivity.this, "Failed To Upload Photo", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}