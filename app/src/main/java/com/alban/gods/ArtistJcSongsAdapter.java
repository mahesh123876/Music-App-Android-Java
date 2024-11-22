package com.alban.gods;


import static com.alban.gods.MyApplication.application;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistJcSongsAdapter extends RecyclerView.Adapter<ArtistJcSongsAdapter.ViewHolder> {
    private int AseletedPosition;
    Context context;
    List<Artist_Get_Songs> artist_get_songs;
    private  ArtistRecyclerItemClickListener artistRecyclerItemClickListener;


    public ArtistJcSongsAdapter(Context context, List<Artist_Get_Songs> artist_get_songs, ArtistRecyclerItemClickListener artistRecyclerItemClickListener) {
        this.context = context;
        this.artist_get_songs = artist_get_songs;
        this.artistRecyclerItemClickListener = artistRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.artistsongrow,parent,false);
        
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        Artist_Get_Songs get_songs=artist_get_songs.get(position);
        if(get_songs!=null){
            if (AseletedPosition==position){

                holder.Aiv_play_active.setVisibility(View.VISIBLE);
            }
            else {

                holder.Aiv_play_active.setVisibility(View.INVISIBLE);
            }
        }
        holder.Atv_title.setText(get_songs.getGsongTitle());
        holder.Atv_artist.setText(get_songs.getGartist());
        String Aeduration=ArtistUtility.GconvertDuration(Long.parseLong(get_songs.getGsongDuration()));
        holder.Atv_duration.setText(Aeduration);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.music).error(R.drawable.music);
        Glide.with(context).load(application.getArturlmain()).apply(requestOptions).into(holder.circleImageView);
        holder.bind(get_songs,artistRecyclerItemClickListener);

    }

    @Override
    public int getItemCount() {
        return artist_get_songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Atv_title,Atv_artist,Atv_duration;
        ImageView Aiv_play_active;
        CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Atv_title=itemView.findViewById(R.id.ar_tv_title);
            Atv_artist=itemView.findViewById(R.id.ar_tv_artist);
            Atv_duration=itemView.findViewById(R.id.ar_tv_duration);
            Aiv_play_active=itemView.findViewById(R.id.ar_play_active);
            circleImageView=itemView.findViewById(R.id.ar_iv_artwork);
        }
        public void bind(Artist_Get_Songs get_songs,ArtistRecyclerItemClickListener artistRecyclerItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url=application.getArturlmain();
                    application.setSongCircle(true);
                    application.setSongUrl(url);
                    application.setCommanurlenable(true);
                    application.setArturl(url);
                    artistRecyclerItemClickListener.onClickListener(get_songs,getAdapterPosition());
                }
            });
        }
    }

    public interface ArtistRecyclerItemClickListener {
        void onClickListener(Artist_Get_Songs Asongs,int gposition);
    }
    public int getAseletedPosition() {
        return AseletedPosition;
    }

    public void setAseletedPosition(int aseletedPosition) {
        AseletedPosition = aseletedPosition;
    }
}
