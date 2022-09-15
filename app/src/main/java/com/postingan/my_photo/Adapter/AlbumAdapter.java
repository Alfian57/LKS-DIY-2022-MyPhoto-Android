package com.postingan.my_photo.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.postingan.my_photo.Model.Album;
import com.postingan.my_photo.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Album> data;
    OnItemClickListener onItemClickListener;

    public AlbumAdapter(List<Album> data, OnItemClickListener onItemClickListener){
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof AlbumViewHolder){
            AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
            albumViewHolder.txtAlbumName.setText(data.get(position).getName());

            albumViewHolder.llAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onDetailClick(data.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface OnItemClickListener{
        void onDetailClick(int id);
    }

    private class AlbumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llAlbum;
        TextView txtAlbumName;

        public AlbumViewHolder(View view) {
            super(view);

            llAlbum = view.findViewById(R.id.llAlbumHome);
            txtAlbumName = view.findViewById(R.id.txtAlbumNameHome);
        }
    }
}
