package com.alban.gods.Adapter;

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

import com.alban.gods.MainActivity;
import com.alban.gods.MyApplication;
import com.alban.gods.R;
import com.alban.gods.searchview;
import com.bumptech.glide.Glide;


import java.util.List;

public class GenereRecycleViewAdapter extends RecyclerView.Adapter<GenereRecycleViewAdapter.ViewHolder> {
    Context context;
    List<Genere_Upload> uploads;

    public GenereRecycleViewAdapter(Context context, List<Genere_Upload> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_genere,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Genere_Upload genereUpload=uploads.get(position);
        holder.GCtv_book_title.setText(genereUpload.getName());
        Glide.with(context).load(genereUpload.getUrl()).into(holder.GCimd_book_thumnail);

        holder.GCcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyApplication.application.isSearch()){
                    searchview.searchview.onBackPressed();

                    Intent intent=new Intent(context, GenereSongsActivity.class);
                    intent.putExtra("Gsong_category",genereUpload.getSongCategory());
                    MyApplication.application.setSonggenereurl(genereUpload.getUrl());
                    MyApplication.application.setGenerecate(genereUpload.getSongCategory());
                    context.startActivity(intent);
                }
                else{
                    MainActivity.mainActivity.finish();
                    Intent intent=new Intent(context, GenereSongsActivity.class);
                    intent.putExtra("Gsong_category",genereUpload.getSongCategory());
                    MyApplication.application.setSonggenereurl(genereUpload.getUrl());
                    MyApplication.application.setGenerecate(genereUpload.getSongCategory());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView GCtv_book_title;
        ImageView GCimd_book_thumnail;
        CardView GCcardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GCtv_book_title=itemView.findViewById(R.id.Genere_book_title_id);
            GCimd_book_thumnail=itemView.findViewById(R.id.Genere_book_img_id);
            GCcardView=itemView.findViewById(R.id.Genere_card_view_id);
            if(MyApplication.application.isEnabledarkmode()){
                itemView.setBackgroundColor(0xff121212);
                GCtv_book_title.setTextColor(0xffffffff);
            }


        }
    }
}
