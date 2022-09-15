package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.RegisterData;

public class RegisterResponse{

	@SerializedName("data")
	private RegisterData data;

	public RegisterData getData(){
		return data;
	}
}