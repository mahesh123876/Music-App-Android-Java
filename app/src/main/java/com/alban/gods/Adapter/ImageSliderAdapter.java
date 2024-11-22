package com.alban.gods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alban.gods.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {
Context context;
ArrayList<ImageSliderModel> imageSliderModelArrayList;

    public ImageSliderAdapter(Context context, ArrayList<ImageSliderModel> imageSliderModelArrayList) {
        this.context = context;
        this.imageSliderModelArrayList = imageSliderModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v=LayoutInflater.from(context).inflate(R.layout.newdesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageSliderModel imageSliderModel=imageSliderModelArrayList.get(position);
        String errimagUri=null;
        errimagUri=imageSliderModel.getFakeimage();
        Picasso.get().load(errimagUri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageSliderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagefx);
        }
    }
}
