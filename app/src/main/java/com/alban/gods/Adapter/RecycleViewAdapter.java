package com.alban.gods.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.github.furkankaplan.fkblurview.FKBlurView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.SongsActivity;
import Model.Upload;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

    private Context mcontext;
    private List <Upload> uploads;
     private  RecyclerviewInterface recyclerviewInterface;

    public RecycleViewAdapter(Context mcontext, List<Upload>uploads){
        this.mcontext=mcontext;
        this.uploads=uploads;
    }

    public void setRecyclerviewInterface(RecyclerviewInterface recyclerviewInterface) {
        this.recyclerviewInterface = recyclerviewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(mcontext).inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Upload upload = uploads.get(position);
          holder.tv_book_title.setText(upload.getName());
          Picasso.get().load(upload.getUrl()).into(holder.tv_book_thumnail);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.application.isSearch()){
                    searchview.searchview.finish();
                    Intent intent = new Intent(mcontext, SongsActivity.class);
                    String se="ID";
                    intent.putExtra("ID",se);
                    MyApplication.application.setSearchtxt(se);
                    intent.putExtra("songimage",upload.getUrl());
                    MyApplication.application.setSongmainurl(upload.getUrl());
                    MyApplication.application.setSongscate(upload.getSongCategory());

                    Log.v("RecycleAdapter","songCategory "+upload.getSongCategory());
                    mcontext.startActivity(intent);
                }
                else{
                    MainActivity.mainActivity.finish();
                    Intent intent = new Intent(mcontext, SongsActivity.class);
                    intent.putExtra("songCategory",upload.getSongCategory());
                    intent.putExtra("songimage",upload.getUrl());
                    MyApplication.application.setSongmainurl(upload.getUrl());
                    MyApplication.application.setSongscate(upload.getSongCategory());

                    Log.v("RecycleAdapter","songCategory "+upload.getSongCategory());
                    mcontext.startActivity(intent);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_book_text;
        TextView tv_book_title;
        ImageView tv_book_thumnail;
        CardView cardView;
        FKBlurView fkBlurView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_thumnail=itemView.findViewById(R.id.book_img_id);
            tv_book_title=itemView.findViewById(R.id.book_title_id);
            cardView=itemView.findViewById(R.id.card_view_id);

            if(MyApplication.application.isEnabledarkmode()){
                itemView.setBackgroundColor(0xff121212);
                tv_book_title.setTextColor(0xffffffff);
            }

        }


    }
    public void changeList(ArrayList<Upload> newList) {
       uploads = newList;
        notifyDataSetChanged();
    }

}
