package com.postingan.my_photo.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.postingan.my_photo.Adapter.AlbumAdapter;
import com.postingan.my_photo.Adapter.PhotoAdapter;
import com.postingan.my_photo.Api.Response.CreateAlbumResponse;
import com.postingan.my_photo.Api.Response.GetAlbumResponse;
import com.postingan.my_photo.Api.Response.GetPhotoResponse;
import com.postingan.my_photo.ViewModel.HomeActivityViewModel;
import com.postingan.my_photo.databinding.ActivityHomeBinding;

import java.io.File;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    HomeActivityViewModel homeActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddAlbum.setVisibility(View.INVISIBLE);
        binding.btnAddPhoto.setVisibility(View.INVISIBLE);

        homeActivityViewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);

        homeActivityViewModel.getAlbum().observe(this, new Observer<GetAlbumResponse>() {
            @Override
            public void onChanged(GetAlbumResponse getAlbumResponse) {
                if (getAlbumResponse != null){
                    if(getAlbumResponse.getAlbums() != null){
                        AlbumAdapter albumAdapter = new AlbumAdapter(getAlbumResponse.getAlbums(), new AlbumAdapter.OnItemClickListener() {
                            @Override
                            public void onDetailClick(int id) {
                                Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                                i.putExtra("albumId", id);
                                startActivity(i);
                            }
                        });

                        binding.rvAlbums.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
                        binding.rvAlbums.setAdapter(albumAdapter);
                        albumAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Failed To Get Albums", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeActivityViewModel.getPhoto().observe(this, new Observer<GetPhotoResponse>() {
            @Override
            public void onChanged(GetPhotoResponse getPhotoResponse) {
                if (getPhotoResponse != null){
                    if(getPhotoResponse.getPhoto() != null){
                        PhotoAdapter photoAdapter = new PhotoAdapter(getPhotoResponse.getPhoto(), HomeActivity.this, new PhotoAdapter.OnItemClickListener() {
                            @Override
                            public void onDeleteClick(String name, String Url) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setTitle("Download Image");
                                builder.setMessage("Are you want to download this Image ?");
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.setPositiveButton("Yes, Download", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadImageNew(name, Url);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                        binding.rvPhotos.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
                        binding.rvPhotos.setAdapter(photoAdapter);
                        photoAdapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(HomeActivity.this, "Failed To Get Photos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeActivityViewModel.getCreateAlbum().observe(this, new Observer<CreateAlbumResponse>() {
            @Override
            public void onChanged(CreateAlbumResponse createAlbumResponse) {
                if (createAlbumResponse != null){
                    if(createAlbumResponse.getData() != null){
                        Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                        i.putExtra("albumId", createAlbumResponse.getData().getId());
                        startActivity(i);
                        Toast.makeText(HomeActivity.this, "Success To Create Album", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.btnAddAlbum.getVisibility() == View.INVISIBLE) {
                    binding.btnAddAlbum.setVisibility(View.VISIBLE);
                    binding.btnAddPhoto.setVisibility(View.VISIBLE);
                } else {
                    binding.btnAddAlbum.setVisibility(View.INVISIBLE);
                    binding.btnAddPhoto.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.btnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        binding.btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Pick An Album First", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertDialog(){
        final EditText editText = new EditText(HomeActivity.this);
        editText.setHint("Album Name");
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Create Album");
        builder.setView(editText);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().length() == 0){
                    editText.setError("Field Still Empty");
                } else {
                    homeActivityViewModel.createAlbum(editText.getText().toString());
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage){
        try{
            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }

}