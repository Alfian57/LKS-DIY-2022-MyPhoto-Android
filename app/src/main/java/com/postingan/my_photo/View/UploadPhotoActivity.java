package com.postingan.my_photo.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.postingan.my_photo.Api.Response.CreatePhotoResponse;
import com.postingan.my_photo.Api.Response.GetUserResponse;
import com.postingan.my_photo.ViewModel.UploadPhotoActivityViewModel;
import com.postingan.my_photo.databinding.ActivityUploadPhotoBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPhotoActivity extends AppCompatActivity {
    ActivityUploadPhotoBinding binding;
    UploadPhotoActivityViewModel uploadPhotoActivityViewModel;
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

        uploadPhotoActivityViewModel = new ViewModelProvider(this).get(UploadPhotoActivityViewModel.class);

        uploadPhotoActivityViewModel.userDetail().observe(this, new Observer<GetUserResponse>() {
            @Override
            public void onChanged(GetUserResponse getUserResponse) {
                if (getUserResponse != null){
                    if (getUserResponse.getName() != null){
                        binding.txtHelloUpload.setText("Hello, " + getUserResponse.getName());
                    }
                } else {
                    Toast.makeText(UploadPhotoActivity.this, "Failed To Get User Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadPhotoActivityViewModel.getUploadPhoto().observe(this, new Observer<CreatePhotoResponse>() {
            @Override
            public void onChanged(CreatePhotoResponse createPhotoResponse) {
                if (createPhotoResponse != null){
                    if (createPhotoResponse.getData() != null){
                        Intent i = new Intent(UploadPhotoActivity.this, DetailActivity.class);
                        i.putExtra("albumId", albumId);
                        startActivity(i);
                        Toast.makeText(UploadPhotoActivity.this, "Success To Upload Photo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UploadPhotoActivity.this, "Failed To Upload Photo", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            imagePath = data.getData();
            uploadImage();
        }
    }

    private void uploadImage(){
        if (getRealPathFromURI(imagePath) != null) {
            File file = new File(getRealPathFromURI(imagePath)); //Get File
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

            uploadPhotoActivityViewModel.uploadPhoto(albumId, body);
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
}