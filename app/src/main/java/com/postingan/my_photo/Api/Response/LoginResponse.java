package com.postingan.my_photo.Api.Response;

import com.google.gson.annotations.SerializedName;
import com.postingan.my_photo.Model.LoginData;

public class LoginResponse{

	@SerializedName("data")
	private LoginData data;

	public LoginData getData(){
		return data;
	}
}