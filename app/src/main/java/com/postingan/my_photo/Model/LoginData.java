package com.postingan.my_photo.Model;

import com.google.gson.annotations.SerializedName;

public class LoginData{

	@SerializedName("signature")
	private String signature;

	public String getSignature(){
		return signature;
	}
}