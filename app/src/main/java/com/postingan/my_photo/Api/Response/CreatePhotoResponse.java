package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.CreatePhotoData;

public class CreatePhotoResponse{

	@SerializedName("data")
	private CreatePhotoData data;

	public CreatePhotoData getData(){
		return data;
	}
}