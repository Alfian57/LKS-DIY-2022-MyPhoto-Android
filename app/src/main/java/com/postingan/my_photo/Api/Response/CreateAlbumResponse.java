package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.CreateAlbumData;

public class CreateAlbumResponse{

	@SerializedName("data")
	private CreateAlbumData data;

	public CreateAlbumData getData(){
		return data;
	}
}