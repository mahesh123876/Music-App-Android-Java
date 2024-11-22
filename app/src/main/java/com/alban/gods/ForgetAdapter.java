package com.alban.gods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForgetAdapter extends RecyclerView.Adapter<ForgetAdapter.Viewholder> {
   Context context;
   List<Userchat> userchatList;

    public ForgetAdapter(Context context, List<Userchat> userchatList) {
        this.context = context;
        this.userchatList = userchatList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.logindesign,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Userchat sModel=userchatList.get(position);
        holder.txtfirst.setText(sModel.getUsernamechat());
        holder.txtlast.setText(sModel.getEmailchat());
        holder.txtpass.setText(sModel.getProfilePicturechat());
        Picasso.get().load(sModel.getProfileurl()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return userchatList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView txtlast,txtfirst,txtpass;
        CircleImageView circleImageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.reccycleimage);
            txtfirst=itemView.findViewById(R.id.txtfirstname);
            txtlast=itemView.findViewById(R.id.txtlastname);
            txtpass=itemView.findViewById(R.id.txtpass);
        }
    }
    public void changeList(ArrayList<Userchat> newList) {
        userchatList = newList;
        notifyDataSetChanged();
    }
}
