package com.alban.gods;

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

import com.squareup.picasso.Picasso;

import java.util.List;



public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
Context context;
List<studentsModel> studentsModelslist;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.designrowforrecycleview,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(studentsModelslist.get(position));
        Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        /*holder.studentsModelObj = studentsModelslist.get(position);
        holder.txtfirst.setText("firstname"+studentsModel.getFirstname());
        holder.txtlast.setText("lastname"+studentsModel.getLastname());
        String imageuri=null;
        imageuri= studentsModel.getImage();
        Picasso.get().load(imageuri).into(holder.imageView);*/
     }

    @Override
    public int getItemCount() {
        return studentsModelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtlast,txtfirst;
        studentsModel studentsModelObj;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.reccycleimage);
            txtfirst=itemView.findViewById(R.id.txtfirstname);
            txtlast=itemView.findViewById(R.id.txtlastname);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    itemClicked();
                    profile.profile.finish();
                }
            });
        }

        public void bind(studentsModel sModel) {
            studentsModelObj = sModel;
            txtfirst.setText(sModel.getFirstname());
            txtlast.setText(sModel.getLastname());
            String imageuri=null;
            imageuri= sModel.getImage();
            Picasso.get().load(imageuri).into(imageView);
        }

        private void itemClicked() {
            lyrics.open(context, studentsModelObj);
        }

    }

    public StudentAdapter(Context context, List<studentsModel> studentsModelslist) {
        this.context = context;
        this.studentsModelslist = studentsModelslist;
    }

    public void changeList(List<studentsModel> newList) {
        studentsModelslist = newList;
        notifyDataSetChanged();
    }
}
