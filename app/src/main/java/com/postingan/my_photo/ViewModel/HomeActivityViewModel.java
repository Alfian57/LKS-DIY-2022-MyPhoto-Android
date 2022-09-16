package com.postingan.my_photo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.Response.CreateAlbumResponse;
import com.postingan.my_photo.Api.Response.GetAlbumResponse;
import com.postingan.my_photo.Api.Response.GetPhotoResponse;
import com.postingan.my_photo.Repository.AlbumRepository;
import com.postingan.my_photo.Repository.PhotoRepository;

public class HomeActivityViewModel extends AndroidViewModel {
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        albumRepository = new AlbumRepository(application);
        photoRepository = new PhotoRepository(application);
    }

    public void createAlbum(String name){
        albumRepository.createAlbum(name);
    }

    public MutableLiveData<CreateAlbumResponse> getCreateAlbum(){
        return albumRepository.getCreateAlbum();
    }

    public MutableLiveData<GetAlbumResponse> getAlbum(){
        return albumRepository.getAlbum();
    }

    public MutableLiveData<GetPhotoResponse> getPhoto(){
        return photoRepository.getPhoto();
    }
}
