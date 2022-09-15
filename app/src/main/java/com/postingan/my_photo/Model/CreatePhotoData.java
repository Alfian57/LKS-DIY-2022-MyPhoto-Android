package com.postingan.my_photo.Model;

import com.google.gson.annotations.SerializedName;

public class CreatePhotoData {

	@SerializedName("size")
	private int size;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("album_id")
	private String albumId;

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	public int getSize(){
		return size;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUserId(){
		return userId;
	}

	public String getName(){
		return name;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getAlbumId(){
		return albumId;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}
}