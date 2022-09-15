package com.postingan.my_photo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.postingan.my_photo.Model.Album;
import com.postingan.my_photo.Model.Photo;
import com.postingan.my_photo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Photo> data;
    Context context;
    OnItemClickListener onItemClickListener;

    public PhotoAdapter(List<Photo> data, Context context, OnItemClickListener onItemClickListener){
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof PhotoViewHolder){
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            photoViewHolder.txtPhotoName.setText(data.get(position).getName());

            Picasso.get()
                    .load(data.get(position).getUrl())
                    .centerCrop()
                    .resize(512, 512)
                    .into(photoViewHolder.image);

            photoViewHolder.llPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onDeleteClick(data.get(position).getName(), data.get(position).getUrl());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onDeleteClick(String name, String Url);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout llPhoto;
        TextView txtPhotoName;

        public PhotoViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.imagePhotoHome);
            llPhoto = view.findViewById(R.id.llPhoto);
            txtPhotoName = view.findViewById(R.id.txtPhotoNameHome);
        }
    }
}
