package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.Photo;

import java.util.List;

public class GetPhotoResponse {
    @SerializedName("data")
    @Expose
    private List<Photo> data = null;

    public List<Photo> getPhoto() {
        return data;
    }

    public void setPhoto(List<Photo> data) {
        this.data = data;
    }
}
