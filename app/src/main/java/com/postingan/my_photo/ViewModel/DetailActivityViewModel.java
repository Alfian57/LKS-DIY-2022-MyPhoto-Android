package com.postingan.my_photo.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.postingan.my_photo.Api.Response.CreateAlbumResponse;
import com.postingan.my_photo.Api.Response.GetPhotoResponse;
import com.postingan.my_photo.Repository.AlbumRepository;
import com.postingan.my_photo.Repository.PhotoRepository;

public class DetailActivityViewModel extends AndroidViewModel {
    private PhotoRepository photoRepository;
    private AlbumRepository albumRepository;

    public DetailActivityViewModel(Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
        albumRepository = new AlbumRepository(application);
    }

    public MutableLiveData<GetPhotoResponse> getPhotoByAlbum(int albumId){
        return photoRepository.getPhotoByAlbum(albumId);
    }

    public void createAlbum(String name){
        albumRepository.createAlbum(name);
    }

    public MutableLiveData<CreateAlbumResponse> getCreateAlbum(){
        return albumRepository.getCreateAlbum();
    }
}
