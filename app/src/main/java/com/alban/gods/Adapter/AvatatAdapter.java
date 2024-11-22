package com.alban.gods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.R;
import com.alban.gods.Userchat;

import java.util.ArrayList;
import java.util.List;


public class AvatatAdapter extends RecyclerView.Adapter<AvatatAdapter.ViewHolder> {

   Context context;
   List<Userchat> userchatList;

    public AvatatAdapter(Context context, List<Userchat> userchatList) {
        this.context = context;
        this.userchatList = userchatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.avatarlayout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Userchat userchat=userchatList.get(position);
    }

    @Override
    public int getItemCount() {
        return userchatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.avatarimage);


        }
    }
    public void changeList(ArrayList<Userchat> newList) {
        userchatList = newList;
        notifyDataSetChanged();
    }

}
