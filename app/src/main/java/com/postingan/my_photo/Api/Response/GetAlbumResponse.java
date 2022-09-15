package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.Album;

import java.util.List;

public class GetAlbumResponse {
    @SerializedName("data")
    @Expose
    private List<Album> albums = null;

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
