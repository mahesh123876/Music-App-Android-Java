package com.alban.gods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Adapter.TrendingUtility;
import com.alban.gods.Adapter.Trending_Get_Songs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrendingJcSonsAdapter extends RecyclerView.Adapter<TrendingJcSonsAdapter.ViewHolder> {
    private int TseletedPosition;
    Context context;
    List<Trending_Get_Songs> trendingGetSongs;
   private TrendingRecyclerViewClickListener trendingRecyclerViewClickListener;

    public TrendingJcSonsAdapter(Context context, List<Trending_Get_Songs> trendingGetSongs, TrendingRecyclerViewClickListener trendingRecyclerViewClickListener) {
        this.context = context;
        this.trendingGetSongs = trendingGetSongs;
        this.trendingRecyclerViewClickListener = trendingRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.designfortrend,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trending_Get_Songs trending_get_songs= trendingGetSongs.get(position);
        if(trending_get_songs!=null){
            Picasso.get().load(trending_get_songs.getAlbum_art()).into(holder.shapeableImageView);
            holder.textview.setText(trending_get_songs.getSongTitle());
            holder.bind(trending_get_songs,trendingRecyclerViewClickListener);
            MyApplication.application.setSongtrendingurl(trending_get_songs.getAlbum_art());
            MyApplication.application.setTrendingsongscate(trending_get_songs.getSongCategory());

        }
    }

    @Override
    public int getItemCount() {
        return trendingGetSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       ShapeableImageView shapeableImageView;
       TextView textview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageView=itemView.findViewById(R.id.Trending_book_img_id_Main);
            textview=itemView.findViewById(R.id.Trending_book_title_id_Main);
            if(MyApplication.application.isEnabledarkmode()){
                itemView.setBackgroundColor(0xff121212);
                textview.setTextColor(0xffffffff);
            }

        }

        public void bind(Trending_Get_Songs trending_get_songs, TrendingRecyclerViewClickListener trendingRecyclerViewClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trendingRecyclerViewClickListener.onClickListener(trending_get_songs,getAdapterPosition());
                }
            });
        }
    }

    public interface TrendingRecyclerViewClickListener {
        void onClickListener(Trending_Get_Songs songs, int postion);
    }

    public int getTSeletedPosition() {
        return TseletedPosition;
    }

    public void setTSeletedPosition(int seletedPosition) {
        this.TseletedPosition = seletedPosition;
    }
}
