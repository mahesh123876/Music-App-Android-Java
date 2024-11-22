package com.alban.gods.Adapter;



import static com.alban.gods.MyApplication.application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Artist_Upload;
import com.alban.gods.MyApplication;
import com.alban.gods.R;
import com.alban.gods.searchview;
import com.bumptech.glide.Glide;

import java.util.List;


public class ArtistRecycleViewAdapter extends RecyclerView.Adapter<ArtistRecycleViewAdapter.ViewHolder> {

    Context context;
    List<Artist_Upload> artist_uploads;

    public ArtistRecycleViewAdapter(Context context, List<Artist_Upload> artist_uploads) {
        this.context = context;
        this.artist_uploads = artist_uploads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.artistdesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artist_Upload upload=artist_uploads.get(position);
        holder.ARtv_book_title.setText(upload.getName());
        Glide.with(context).load(upload.getUrl()).into(holder.ARimd_book_thumnail);
        holder.ARcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchview.searchview.finish();
                Intent intent=new Intent(context, ArtistSongsActivity.class);
                application.setArturlmain(upload.getUrl());
                intent.putExtra("Asong_category",upload.getSongCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artist_uploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ARtv_book_title;
        ImageView ARimd_book_thumnail;
        CardView ARcardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ARtv_book_title=itemView.findViewById(R.id.art_book_title_id);
            ARimd_book_thumnail=itemView.findViewById(R.id.art_book_img_id);
            ARcardView=itemView.findViewById(R.id.art_card_view_id);
            if(MyApplication.application.isEnabledarkmode()){
                itemView.setBackgroundColor(0xff121212);
                ARtv_book_title.setTextColor(0xffffffff);
            }
        }
    }
}
