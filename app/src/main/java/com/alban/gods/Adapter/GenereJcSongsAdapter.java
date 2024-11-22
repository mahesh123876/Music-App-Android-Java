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

import de.hdodenhof.circleimageview.CircleImageView;

public class GenereJcSongsAdapter extends RecyclerView.Adapter<GenereJcSongsAdapter.GeneresSongsViewHolder> {
    private int GseletedPosition;
    Context context;
    List<Genere_Get_Songs> genere_get_songsList;
    private GenereRecyclerItemClickListener genereRecyclerItemClickListener;


    public GenereJcSongsAdapter(Context context, List<Genere_Get_Songs> genere_get_songsList, GenereRecyclerItemClickListener genereRecyclerItemClickListener) {
        this.context = context;
        this.genere_get_songsList = genere_get_songsList;
        this.genereRecyclerItemClickListener = genereRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public GeneresSongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.generesongs_row,parent,false);

        return new GeneresSongsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneresSongsViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        Genere_Get_Songs get_songs=genere_get_songsList.get(position);
        if(get_songs!=null){
            if (GseletedPosition==position){

                holder.Siv_play_active.setVisibility(View.VISIBLE);
            }
            else {

                holder.Siv_play_active.setVisibility(View.INVISIBLE);
            }
        }
        holder.Stv_title.setText(get_songs.getGsongTitle());
        holder.Stv_artist.setText(get_songs.getGartist());
        String Geduration= GenereUtility.GconvertDuration(Long.parseLong(get_songs.getGsongDuration()));
        holder.Stv_duration.setText(Geduration);
        holder.bind(get_songs,genereRecyclerItemClickListener);
        String getgenereurl= MyApplication.application.getSonggenereurl();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.music).error(R.drawable.music);
        Glide.with(context).load(getgenereurl).apply(requestOptions).into(holder.circleImageViewge);

    }

    @Override
    public int getItemCount() {
        return genere_get_songsList.size();
    }

    public class GeneresSongsViewHolder extends RecyclerView.ViewHolder {
        private TextView Stv_title,Stv_artist,Stv_duration;
        ImageView Siv_play_active;
        CircleImageView circleImageViewge;
        public GeneresSongsViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewge=itemView.findViewById(R.id.Giv_artwork);
            Stv_title=itemView.findViewById(R.id.Gtv_title);
            Stv_artist=itemView.findViewById(R.id.Gtv_artist);
            Stv_duration=itemView.findViewById(R.id.Gtv_duration);
            Siv_play_active=itemView.findViewById(R.id.Gplay_active);
        }

        public void bind(Genere_Get_Songs get_songs, GenereRecyclerItemClickListener genereRecyclerItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genereRecyclerItemClickListener.onClickListener(get_songs,getAdapterPosition());
                }
            });
        }
    }

    public interface GenereRecyclerItemClickListener {
void onClickListener(Genere_Get_Songs gsongs,int gposition);
    }

    public int getGseletedPosition() {
        return GseletedPosition;
    }

    public void setGseletedPosition(int gseletedPosition) {
        GseletedPosition = gseletedPosition;
    }
}
