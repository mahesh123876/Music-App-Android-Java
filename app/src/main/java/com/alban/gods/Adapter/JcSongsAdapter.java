package com.alban.gods.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.MyApplication;
import com.alban.gods.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import Model.GetSongs;
import Model.Utility;

import de.hdodenhof.circleimageview.CircleImageView;

public class JcSongsAdapter extends RecyclerView.Adapter<JcSongsAdapter.SongsAdapterViewHolder>{
private int seletedPosition;


    Context context;
    List<GetSongs> arrayListSongs;
    private  RecyclerItemClickListener listener;

    public JcSongsAdapter(Context context,List<GetSongs> arrayListSongs,RecyclerItemClickListener listener){

        this.context=context;
        this.arrayListSongs=arrayListSongs;
        this.listener=listener;
    }

    @NonNull
    @Override
    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.songs_row, parent, false);
        return new SongsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        GetSongs getSongs=arrayListSongs.get(position);
        if(getSongs!=null){
            if (seletedPosition==position){

                holder.iv_play_active.setVisibility(View.VISIBLE);

            }
            else {
                holder.iv_play_active.setVisibility(View.INVISIBLE);
            }
        }
        holder.tv_title.setText(getSongs.getSongTitle());
        holder.tv_artist.setText(getSongs.getArtist());
        String duration= Utility.convertDuration(Long.parseLong(getSongs.getSongDuration()));
        holder.tv_duration.setText(duration);
        String fun= MyApplication.application.getSongmainurl();
        String se=MyApplication.application.getSearchtxt();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.music).error(R.drawable.music);
        Glide.with(context).load(fun).apply(requestOptions).into(holder.circleImageView);
        holder.bind(getSongs,listener);
        int n=getItemCount();


    }

    @Override
    public int getItemCount() {
        return arrayListSongs.size();
    }

    public static class SongsAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title,tv_artist,tv_duration;
        ImageView iv_play_active;
        CircleImageView circleImageView;


        public  SongsAdapterViewHolder(@NonNull View itemView){
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_artist=itemView.findViewById(R.id.tv_artist);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_play_active=itemView.findViewById(R.id.play_active);
            circleImageView=itemView.findViewById(R.id.iv_artwork);
        }

        public void bind(GetSongs getSongs, RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(getSongs,getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerItemClickListener {
        void onClickListener(GetSongs songs,int postion);


    }

    public int getSeletedPosition() {
        return seletedPosition;
    }

    public void setSeletedPosition(int seletedPosition) {
        this.seletedPosition = seletedPosition;
    }
}
