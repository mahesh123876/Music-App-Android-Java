package com.alban.gods.Adapter;



import static com.alban.gods.MyApplication.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.LLogin;
import com.alban.gods.MainActivity;
import com.alban.gods.R;
import com.alban.gods.Setting;
import com.alban.gods.TrendingJcSonsAdapter;
import com.alban.gods.chart;
import com.alban.gods.karaoke;
import com.alban.gods.library;
import com.alban.gods.playmusic;
import com.alban.gods.profile;
import com.alban.gods.searchview;
import com.alban.gods.upload;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class TrendingSongActivity extends AppCompatActivity {
    RecyclerView recyclerViewTrendsong;
    float xDown=0,yDown=0;
    GifImageView progressBarTrend;
    Boolean Tcheckin=false;
    List<Trending_Get_Songs> list;
    TrendingJcSonsAdapter trendingJcSonsAdapter;
    ValueEventListener valueEventListener;
    DatabaseReference databaseReference;
    JcPlayerView jcPlayerViewTrend;
    ArrayList<JcAudio> jcAudiosTrend=new ArrayList<>();
    private  int currentindexTrend;
    String trendingnotify;
    Context c=this;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
     String s;
     ImageView libimageViewcancelnav;
     RelativeLayout relativeLayout;
     CircleImageView circleImageView;
     CircleMenu circleMenu;
    ImageView pro2,power;
    TextView emailfirst,txtemail;
    BottomSheetDialog bottomSheetDialog;

    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trending_song);

        h1=findViewById(R.id.trendhome);
        l1=findViewById(R.id.trendlib);
        p1=findViewById(R.id.trendup);
        s1=findViewById(R.id.trendse);
        pro1=findViewById(R.id.trendlyrics);

        recyclerViewTrendsong=findViewById(R.id.trendsongsllayoutRecycleview);
        progressBarTrend=findViewById(R.id.progressbartrend);
        jcPlayerViewTrend=findViewById(R.id.jcplayertrend);

        libre=findViewById(R.id.proretrend);
        circleImageView=findViewById(R.id.imagviewdragtrend);
        relativeLayout=findViewById(R.id.forlooptrend);
        profilerelative=findViewById(R.id.proprofilefornavtrend);
        helprelative=findViewById(R.id.proHelpfornavtrend);
        karaokerelative=findViewById(R.id.prokaraokefornavtrend);
        chortchartrelative=findViewById(R.id.prochordchartfornavtrend);

        libimageViewcancelnav=findViewById(R.id.proclosenavtrend);
        power=findViewById(R.id.propowertrend);
        txtemail=findViewById(R.id.protxtemailtrend);
        emailfirst=findViewById(R.id.emailfirstprotrend);
        String gmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());
        txtemail.setText(gmail);
        trendingnotify= getIntent().getStringExtra("backt");
        s= getIntent().getStringExtra("Tsong_category");
        GestureDetector gestureDetector=new GestureDetector(c,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {

                playmusic.playmusic.current();
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {

                playmusic.playmusic.playsongs();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDoubleTapEvent(@NonNull MotionEvent e) {

                return super.onDoubleTapEvent(e);
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                finish();
                super.onLongPress(e);
            }
        });

        libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, MainActivity.class);
                startActivity(intent);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, library.class);
                startActivity(intent);
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, upload.class);
                startActivity(intent);
            }
        });
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, searchview.class);
                startActivity(intent);
            }
        });
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, profile.class);
                startActivity(intent);
            }
        });

        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c, Setting.class);
                startActivity(intent);
            }
        });
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog=new BottomSheetDialog(c,R.style.BottomSheetStyle);
                View view= LayoutInflater.from(c).inflate(R.layout.logoutbottom,(RelativeLayout)findViewById(R.id.layoutforloutbottom));
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                ImageView logoutimage;
                GifImageView logoutgif;
                Button btncancel,btnlogout;
                logoutimage=bottomSheetDialog.findViewById(R.id.logoutimage);
                logoutgif=bottomSheetDialog.findViewById(R.id.logoutgif);
                btncancel=bottomSheetDialog.findViewById(R.id.cancel);
                btnlogout=bottomSheetDialog.findViewById(R.id.logout);
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                    }
                });
                btnlogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutimage.setVisibility(View.INVISIBLE);
                        logoutgif.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(c, LLogin.class);
                                startActivity(intent);
                            }
                        },1400);


                    }
                });
            }
        });
        karaokerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c, karaoke.class);
                startActivity(intent);
            }
        });
        chortchartrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c, chart.class);
                startActivity(intent);
            }
        });


        libimageViewcancelnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roate);
                libimageViewcancelnav.startAnimation(animation3);
                libre.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                libre.startAnimation(animation2);
                profilerelative.startAnimation(animation2);
                helprelative.startAnimation(animation2);
                karaokerelative.startAnimation(animation2);
                chortchartrelative.startAnimation(animation2);



            }
        });
        if(application.isEnblecolor()){
            libre.setBackgroundColor(0xff121212);
        }
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, library.class);
                startActivity(intent);
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri=Uri.parse("https://priscillicmusic.com/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, searchview.class);
                startActivity(intent);
            }
        });
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,MainActivity.class);
                startActivity(intent);
            }
        });
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, profile.class);
                startActivity(intent);
            }
        });

        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);
            String ii=application.getSongUrl();
            Picasso.get().load(ii).into(circleImageView);
        }
        recyclerViewTrendsong.setHasFixedSize(true);
        recyclerViewTrendsong.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        trendingJcSonsAdapter=new TrendingJcSonsAdapter(this, list, new TrendingJcSonsAdapter.TrendingRecyclerViewClickListener() {
            @Override
            public void onClickListener(Trending_Get_Songs songs, int postion) {

                changeSelectedSong(postion);
                //jcPlayerViewTrend.playAudio(jcAudiosTrend.get(postion));
               // jcPlayerViewTrend.setVisibility(View.VISIBLE);
                //jcPlayerViewTrend.createNotification();
                Intent intent = new Intent(c, playmusic.class);
                intent.putExtra("trendingsongtitle", songs.getSongTitle());
                intent.putExtra("trendingsonglink", songs.getSonglink());
                intent.putExtra("trendingsongcate", songs.getSongCategory());
                startActivity(intent);
                Animatoo.animateSlideUp(c);


            }
        });

        libimageViewcancelnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roate);
                libimageViewcancelnav.startAnimation(animation3);
                libre.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                libre.startAnimation(animation2);
                profilerelative.startAnimation(animation2);
                helprelative.startAnimation(animation2);
                karaokerelative.startAnimation(animation2);
                chortchartrelative.startAnimation(animation2);
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Trending");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dss:snapshot.getChildren()) {
                    Trending_Get_Songs getSongs=dss.getValue(Trending_Get_Songs.class);
                    getSongs.getmKey(dss.getKey());
                    currentindexTrend=0;
                    if (s != null && s.equals(getSongs.getSongCategory())||trendingnotify!=null&&trendingnotify.equals(getSongs.getSongCategory())) {
                        list.add(getSongs);
                        Tcheckin = true;
                        jcAudiosTrend.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSonglink()));
                    }

                }

                trendingJcSonsAdapter.setTSeletedPosition(0);
                trendingJcSonsAdapter.notifyDataSetChanged();
                progressBarTrend.setVisibility(View.GONE);
                recyclerViewTrendsong.setAdapter(trendingJcSonsAdapter);
                if(Tcheckin){
                    if(s!=null){
                        application.setTrendingnotify(s);
                    }
                    if(trendingnotify!=null){
                        application.setTrendingnotify(trendingnotify);
                    }

                    jcPlayerViewTrend.initPlaylist(jcAudiosTrend,null);
                }
                else{
                    try {
                        Intent intent = new Intent();
                        intent.setAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
                        sendBroadcast(intent);
                    } catch(Exception e){

                    }
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBarTrend.setVisibility(View.GONE);
            }
        });
        circleImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);

                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        xDown=motionEvent.getX();
                        yDown=motionEvent.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float Movedx,Movedy;
                        Movedx=motionEvent.getX();
                        Movedy=motionEvent.getY();

                        float distancex=Movedx-xDown;
                        float distancey=Movedy-yDown;

                        relativeLayout.setX(relativeLayout.getX()+distancex);
                        relativeLayout.setY(relativeLayout.getY()+distancey);




                        xDown=Movedx;
                        yDown=Movedy;

                        break;

                }
                return true;
            }
        });
    }
    public void  changeSelectedSong(int index){
        trendingJcSonsAdapter.notifyItemChanged(trendingJcSonsAdapter.getTSeletedPosition());
        currentindexTrend=index;
        trendingJcSonsAdapter.setTSeletedPosition(currentindexTrend);
        trendingJcSonsAdapter.notifyItemChanged(currentindexTrend);
    }
    public void showPopup(View v){
        libre.setVisibility(View.VISIBLE);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        libre.startAnimation(animation1);
        profilerelative.startAnimation(animation1);
        helprelative.startAnimation(animation1);
        karaokerelative.startAnimation(animation1);
        chortchartrelative.startAnimation(animation1);

    }

}