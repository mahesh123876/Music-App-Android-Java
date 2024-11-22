package com.alban.gods.Adapter;

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
import com.alban.gods.MyApplication;
import com.alban.gods.R;
import com.alban.gods.Setting;
import com.alban.gods.chart;
import com.alban.gods.karaoke;
import com.alban.gods.library;
import com.alban.gods.playmusic;
import com.alban.gods.profile;
import com.alban.gods.searchview;
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

public class GenereSongsActivity extends AppCompatActivity {


RecyclerView recyclerViewgeneresong;
GifImageView progressBargenere;
Boolean Gcheckin=false;
List<Genere_Get_Songs> list;
GenereJcSongsAdapter genereJcSongsAdapter;
ValueEventListener valueEventListener;
DatabaseReference databaseReference;
JcPlayerView jcPlayerViewgenere;
ArrayList<JcAudio> jcAudiosgenere=new ArrayList<>();
private  int currentindexgenere;
Context c=this;
MyApplication application;
String s;
BottomSheetDialog bottomSheetDialog;
RelativeLayout relativeLayoutgenere;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    ImageView libimageViewcancelnav;

String generenotify;
float xDown=0,yDown=0;
CircleImageView circleImageViewgenere;
CircleMenu circleMenugenere;
    ImageView pro2,power;

    TextView txtemail,emailfirst;

    LinearLayout h1,s1,p1, l1,pro1;
    ImageView pause,play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication)getApplication();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_genere_songs);
        play=findViewById(R.id.playicongenere);
        pause=findViewById(R.id.pauseicongenere);
        h1=findViewById(R.id.generehome);
        l1=findViewById(R.id.generelib);
        p1=findViewById(R.id.genereup);
        s1=findViewById(R.id.generese);
        pro1=findViewById(R.id.generelyrics);

        power=findViewById(R.id.propowerge);
        recyclerViewgeneresong=findViewById(R.id.recycleviewgenereSongs);
        progressBargenere=findViewById(R.id.genereprogress);
        jcPlayerViewgenere=findViewById(R.id.genereJcplayer);
        relativeLayoutgenere=findViewById(R.id.genereforloop);
        generenotify=getIntent().getStringExtra("backg");
        s=getIntent().getStringExtra("Gsong_category");
        circleImageViewgenere=findViewById(R.id.imagviewdraggenere);
        libre=findViewById(R.id.prorege);
        profilerelative=findViewById(R.id.proprofilefornavge);
        helprelative=findViewById(R.id.proHelpfornavge);
        karaokerelative=findViewById(R.id.prokaraokefornavge);
        chortchartrelative=findViewById(R.id.prochordchartfornavge);

        libimageViewcancelnav=findViewById(R.id.proclosenavge);


        txtemail=findViewById(R.id.protxtemail);
        emailfirst=findViewById(R.id.emailfirstproge);
        String gmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());
        txtemail.setText(gmail);
        GestureDetector gestureDetector=new GestureDetector(c,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {

                playmusic.playmusic.current();
                Animation animationfadein=AnimationUtils.loadAnimation(c,R.anim.fadein);
                pause.startAnimation(animationfadein);
                pause.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation anifadeout=AnimationUtils.loadAnimation(c,R.anim.fadeout);
                        pause.startAnimation(anifadeout);
                        pause.setVisibility(View.GONE);
                    }
                },1400);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {

                playmusic.playmusic.playsongs();


                Animation animationfadeinplay=AnimationUtils.loadAnimation(c,R.anim.fadein);
                Animation animationfadeoutplay=AnimationUtils.loadAnimation(c,R.anim.fadeout);
                play.startAnimation(animationfadeinplay);
                play.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        play.startAnimation(animationfadeoutplay);
                        play.setVisibility(View.GONE);

                    }
                },1400);
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
        if(application.getSongCircle()){
            relativeLayoutgenere.setVisibility(View.VISIBLE);
            String ii=application.getSongUrl();
            Picasso.get().load(ii).into(circleImageViewgenere);
        }
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
                Intent intent=new Intent(c, MainActivity.class);
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

    circleImageViewgenere.setOnTouchListener(new View.OnTouchListener() {

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
                        relativeLayoutgenere.setX(relativeLayoutgenere.getX()+distancex);
                        relativeLayoutgenere.setY(relativeLayoutgenere.getY()+distancey);




                        xDown=Movedx;
                        yDown=Movedy;

                        break;

                }
                return true;
            }
        });
        if(application.getSongCircle()){
            relativeLayoutgenere.setVisibility(View.VISIBLE);
        }

        String ii=application.getSongUrl();
        Picasso.get().load(ii).into(circleImageViewgenere);


        recyclerViewgeneresong.setHasFixedSize(true);
        recyclerViewgeneresong.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();

        genereJcSongsAdapter=new GenereJcSongsAdapter(this, list, new GenereJcSongsAdapter.GenereRecyclerItemClickListener() {
            @Override
            public void onClickListener(Genere_Get_Songs gsongs, int gposition) {
                changeSelectedSong(gposition);
                //jcPlayerViewgenere.playAudio(jcAudiosgenere.get(gposition));
                //jcPlayerViewgenere.setVisibility(View.VISIBLE);
                //jcPlayerViewgenere.createNotification();
                Intent intent = new Intent(c, playmusic.class);
                intent.putExtra("generesongtitle", gsongs.getGsongTitle());
                intent.putExtra("generesonglink", gsongs.getGsonglink());
                intent.putExtra("generesongcate", gsongs.getGsongCategory());
                startActivity(intent);
                Animatoo.animateSlideUp(c);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Genere");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dss:snapshot.getChildren()) {
                    Genere_Get_Songs get_songs=dss.getValue(Genere_Get_Songs.class);
                    get_songs.getGmKey(dss.getKey());

                    currentindexgenere=0;
                    if (s != null && s.equals(get_songs.getGsongCategory())||generenotify!=null&&generenotify.equals(get_songs.getGsongCategory())) {
                        list.add(get_songs);
                        Gcheckin = true;
                        jcAudiosgenere.add(JcAudio.createFromURL(get_songs.getGsongTitle(), get_songs.getGsonglink()));
                    }
                }
                recyclerViewgeneresong.setAdapter(genereJcSongsAdapter);
                genereJcSongsAdapter.setGseletedPosition(0);
                genereJcSongsAdapter.notifyDataSetChanged();
                progressBargenere.setVisibility(View.GONE);
                if(Gcheckin){
                    if(s!=null){
                        application.setGenrenotify(s);
                    }
                    if(generenotify!=null){
                        application.setGenrenotify(generenotify);
                    }
                    jcPlayerViewgenere.initPlaylist(jcAudiosgenere,null);
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
                progressBargenere.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c, MainActivity.class);
        startActivity(intent);

    }

    public void  changeSelectedSong(int index){
        genereJcSongsAdapter.notifyItemChanged(genereJcSongsAdapter.getGseletedPosition());
        currentindexgenere=index;
        genereJcSongsAdapter.setGseletedPosition(currentindexgenere);
        genereJcSongsAdapter.notifyItemChanged(currentindexgenere);

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