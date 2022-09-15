package com.postingan.my_photo.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Auth {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Auth(Context context){
        sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setToken(String token){
        editor.putString("TOKEN", "Bearer " + token);
        editor.commit();
        editor.apply();
    }

    public void setNullToken(){
        editor.putString("TOKEN", null);
        editor.commit();
        editor.apply();
    }

    public String getToken(){
        return sharedPreferences.getString("TOKEN", null);
    }

}
