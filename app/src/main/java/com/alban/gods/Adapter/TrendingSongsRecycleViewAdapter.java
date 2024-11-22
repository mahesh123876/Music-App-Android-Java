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
import com.bumptech.glide.Glide;


import java.util.List;

public class TrendingSongsRecycleViewAdapter extends RecyclerView.Adapter<TrendingSongsRecycleViewAdapter.ViewHolder> {

    Context context;
    List<Trending_Upload> trendingUploadList;

    public TrendingSongsRecycleViewAdapter(Context context, List<Trending_Upload> trendingUploadList) {
        this.context = context;
        this.trendingUploadList = trendingUploadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v=LayoutInflater.from(context).inflate(R.layout.trendingsongsdesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Trending_Upload trending_upload=trendingUploadList.get(position);
        holder.TNtv_book_title.setText(trending_upload.getName());
        Glide.with(context).load(trending_upload.getUrl()).into(holder.TNimd_book_thumnail);
        holder.TNcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.mainActivity.finish();
                Intent intent=new Intent(context, TrendingSongActivity.class);
                intent.putExtra("Tsong_category",trending_upload.getSongCategory());
                MyApplication.application.setSongtrendingurl(trending_upload.getUrl());
                MyApplication.application.setTrendingsongscate(trending_upload.getSongCategory());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return trendingUploadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TNtv_book_title;
        ImageView TNimd_book_thumnail;
        CardView TNcardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TNtv_book_title=itemView.findViewById(R.id.Trending_book_title_id);
            TNimd_book_thumnail=itemView.findViewById(R.id.Trending_book_img_id);
            TNcardView=itemView.findViewById(R.id.Trending_card_view_id);
            if(MyApplication.application.isEnabledarkmode()){
                itemView.setBackgroundColor(0xff121212);
                TNtv_book_title.setTextColor(0xffffffff);
            }

        }
    }
}
