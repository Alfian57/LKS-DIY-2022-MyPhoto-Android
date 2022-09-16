package com.postingan.my_photo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.Response.CreatePhotoResponse;
import com.postingan.my_photo.Api.Response.GetUserResponse;
import com.postingan.my_photo.Repository.PhotoRepository;
import com.postingan.my_photo.Repository.UserRepository;

import okhttp3.MultipartBody;

public class UploadPhotoActivityViewModel extends AndroidViewModel {
    private PhotoRepository photoRepository;
    private UserRepository userRepository;

    public UploadPhotoActivityViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<GetUserResponse> userDetail(){
        return userRepository.userDetail();
    }

    public void uploadPhoto(Integer albumId, MultipartBody.Part body){
        photoRepository.uploadImage(albumId, body);
    }

    public MutableLiveData<CreatePhotoResponse> getUploadPhoto(){
        return photoRepository.getUploadImage();
    }
}
