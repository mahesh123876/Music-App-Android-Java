package com.alban.gods.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alban.gods.LLogin;
import com.alban.gods.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAvatarAdapter extends RecyclerView.Adapter<AdminAvatarAdapter.ViewHolder> {

    Context context;
    List<Avatardata> userchatList;

    public AdminAvatarAdapter(Context context, List<Avatardata> userchatList) {
        this.context = context;
        this.userchatList = userchatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.designforavatar,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Avatardata avatardata=userchatList.get(position);
        Picasso.get().load(avatardata.getAvatarurl()).into(holder.circleImageView);
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LLogin.class);
                intent.putExtra("imageurlforavatar",avatardata.getAvatarurl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userchatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.circleimageviewforavatar);


        }
    }
}
