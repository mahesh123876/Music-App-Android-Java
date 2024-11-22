package com.alban.gods.Adapter;

import static android.content.Intent.ACTION_VIEW;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.MainActivity;
import com.alban.gods.MyApplication;
import com.alban.gods.R;
import com.alban.gods.Setting;
import com.alban.gods.Userchat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileuseAdapter extends RecyclerView.Adapter<ProfileuseAdapter.ViewHoler> {

Context context;
List<Userchat> userchatList;

    public ProfileuseAdapter(Context context, List<Userchat> userchatList) {
        this.context = context;
        this.userchatList = userchatList;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.forprofileuse,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        Userchat userchat=userchatList.get(position);
        holder.t1.setText(userchat.getUsernamechat());
        holder.t2.setText(userchat.getEmailchat());
        holder.t3.setText(userchat.getProfilePicturechat());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.baseline_account_circle_24).error(R.drawable.baseline_account_circle_24);
        Glide.with(context).load(userchat.getProfileurl()).apply(requestOptions).into(holder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return userchatList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,nosongs,nolyrics,devbylifeind;
        CircleImageView circleImageView;
        RelativeLayout eyeshow,eyehide;
        ImageView backarrow;

        CardView cardViewdarkmode;
        BottomSheetDialog bottomSheetDialog;
        int songs;
        int genere;
        int trend;
        int art;
        int totalsongs;
        int lyrics;
        ImageView feedback;
        Dialog dialog;
        DatabaseReference databaseReference;


        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.txtfirstnamepro);
            t2=itemView.findViewById(R.id.txtlastnamepro);
            t3=itemView.findViewById(R.id.txtpasspro);
            devbylifeind=itemView.findViewById(R.id.developedbyind);
            nosongs=itemView.findViewById(R.id.numberofsongs);
            nolyrics=itemView.findViewById(R.id.numberoflyrics);
            eyeshow=itemView.findViewById(R.id.eyeshow);
            eyehide=itemView.findViewById(R.id.eyehide);
            feedback=itemView.findViewById(R.id.feedback);
            circleImageView=itemView.findViewById(R.id.reccycleimagepro);
            backarrow=itemView.findViewById(R.id.backarrowforprofile);
            cardViewdarkmode=itemView.findViewById(R.id.darkmodecardview);


            songs= MyApplication.application.getCalbum();
            trend= MyApplication.application.getCtrend();
            genere=MyApplication.application.getCgenere();
            art=MyApplication.application.getCart();
            lyrics=MyApplication.application.getLyrics();
            totalsongs=songs+trend+genere+art;
            startCountAnimationforsongs();
            startCountAnimationforlyrics();
            dialog=new Dialog(context);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dailogfeedback);
            CardView cardView;
            EditText textfieldfeedback;
            cardView=dialog.findViewById(R.id.okcardfeedback);
            textfieldfeedback=dialog.findViewById(R.id.feedbacktxtfield);
            SpannableString content = new SpannableString("Developed and Owned By Life Changers Ind");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            devbylifeind.setText(content);

            databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks");
            devbylifeind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri=Uri.parse("https://lifechangersind.com/");
                    Intent intent=new Intent(ACTION_VIEW,uri);
                    context.startActivity(intent);
                }
            });
            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();

                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String feedbacktxt=textfieldfeedback.getText().toString();
                    if(feedbacktxt.length()>=1){
                        DatabaseReference newpost=databaseReference.push();
                        newpost.child("feedbacktxt").setValue(feedbacktxt);
                        Toast.makeText(context, "successfully added your feedback", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        textfieldfeedback.setError("Something went wrong");
                        Toast.makeText(context, "Type the feedback", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            eyeshow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    eyeshow.setVisibility(View.INVISIBLE);
                    eyehide.setVisibility(View.VISIBLE);
                    t3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            });
            eyehide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eyehide.setVisibility(View.INVISIBLE);
                    eyeshow.setVisibility(View.VISIBLE);
                    t3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            });
            cardViewdarkmode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetStyle);
                    View view= LayoutInflater.from(context).inflate(R.layout.darkmode,(RelativeLayout)itemView.findViewById(R.id.bottomrelativce));
                    bottomSheetDialog.setContentView(view);
                    bottomSheetDialog.show();
                    CardView dark,light;
                    dark=bottomSheetDialog.findViewById(R.id.dark);
                    light=bottomSheetDialog.findViewById(R.id.light);
                    dark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyApplication.application.setEnabledarkmode(true);
                            MyApplication.application.setEnblecolor(true);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                    });
                    light.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyApplication.application.setEnabledarkmode(false);
                            MyApplication.application.setEnblecolor(false);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                    });
                }
            });
            backarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setting.setting.finish();
                    Intent intent=new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }
            });

        }
        private void startCountAnimationforsongs() {
            ValueAnimator animator = ValueAnimator.ofInt(0, totalsongs);
            animator.setDuration(5000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    nosongs.setText(animation.getAnimatedValue().toString());
                }
            });
            animator.start();
        }
        private void startCountAnimationforlyrics() {
            ValueAnimator animator = ValueAnimator.ofInt(0, lyrics);
            animator.setDuration(5000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    nolyrics.setText(animation.getAnimatedValue().toString());
                }
            });
            animator.start();
        }
    }
}
