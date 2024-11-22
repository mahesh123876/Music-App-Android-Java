package com.alban.gods.Adapter;



import static com.alban.gods.MyApplication.application;

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

import com.alban.gods.ArtistJcSongsAdapter;
import com.alban.gods.Artist_Get_Songs;
import com.alban.gods.LLogin;
import com.alban.gods.MainActivity;
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

public class ArtistSongsActivity extends AppCompatActivity {
    RecyclerView recyclerViewartistsong;
    GifImageView progressBarartist;
    Boolean Acheckin=false;
    List<Artist_Get_Songs> list;
    ArtistJcSongsAdapter artistJcSongsAdapter;
    ValueEventListener valueEventListener;
    DatabaseReference databaseReference;
    JcPlayerView jcPlayerViewArtist;
    ArrayList<JcAudio> jcAudiosartist=new ArrayList<>();
    private  int currentindexartist;
    Context c=this;
     String s,artnotify;
     RelativeLayout relativeLayout;
     CircleImageView circleImageView;
     CircleMenu circleMenu;
    float xDown=0,yDown=0;
    ImageView libimageViewcancelnav;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    BottomSheetDialog bottomSheetDialog;
    TextView textViewproemail;
    ImageView powerpro;
    TextView emailfirstpro;
    ImageView pro2,voice,pausepro,playpro;


    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1;
    ImageView pause,play;


    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c, searchview.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_artist_songs);
        powerpro=findViewById(R.id.propowerart);
        play=findViewById(R.id.playiconart);
        pause=findViewById(R.id.pauseiconart);
        textViewproemail=findViewById(R.id.protxtemailart);
        emailfirstpro=findViewById(R.id.emailfirstproart);
        libre=findViewById(R.id.proreart);
        profilerelative=findViewById(R.id.proprofilefornavart);
        helprelative=findViewById(R.id.proHelpfornavart);
        karaokerelative=findViewById(R.id.prokaraokefornavart);
        chortchartrelative=findViewById(R.id.prochordchartfornavart);
        libimageViewcancelnav=findViewById(R.id.proclosenavart);
        relativeLayout=findViewById(R.id.genereforloopart);
        circleImageView=findViewById(R.id.imagviewdraggenereart);
        recyclerViewartistsong=findViewById(R.id.recycleviewforartist);
        progressBarartist=findViewById(R.id.artistprogress);
        jcPlayerViewArtist=findViewById(R.id.artistJcplayer);
        String gmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstpro.setText(getgmail.toUpperCase());
        recyclerViewartistsong.setHasFixedSize(true);
        s = getIntent().getExtras().getString("Asong_category");
        artnotify=getIntent().getStringExtra("backa");
        recyclerViewartistsong.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();

        if(application.isEnabledarkmode()){
            libre.setBackgroundColor(0xff121212);
        }
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

        libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        h1=findViewById(R.id.arthome);
        l1=findViewById(R.id.artlib);
        p1=findViewById(R.id.artup);
        s1=findViewById(R.id.artse);
        pro1=findViewById(R.id.artlyrics);
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c, profile.class);
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

                Uri uri=Uri.parse("https://priscillicmusic.com/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,searchview.class);
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





        powerpro.setOnClickListener(new View.OnClickListener() {
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
        textViewproemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c, Setting.class);
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



        artistJcSongsAdapter=new ArtistJcSongsAdapter(c, list, new ArtistJcSongsAdapter.ArtistRecyclerItemClickListener() {
            @Override
            public void onClickListener(Artist_Get_Songs Asongs, int gposition) {
               /* changeSelectedSong(gposition);
                jcPlayerViewArtist.playAudio(jcAudiosartist.get(gposition));
                jcPlayerViewArtist.setVisibility(View.VISIBLE);
                jcPlayerViewArtist.createNotification();
                MyApplication.application.setEnableart(true);*/
               finish();
                Intent intent=new Intent(c,playmusic.class);
                intent.putExtra("Artsongtitle",Asongs.getGsongTitle());
                intent.putExtra("Artsonglink",Asongs.getGsonglink());
                intent.putExtra("Artsongcate",Asongs.getGsongCategory());
                startActivity(intent);
                Animatoo.animateSlideUp(c);

            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Artist");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dss:snapshot.getChildren()) {
                    Artist_Get_Songs get_songs=dss.getValue(Artist_Get_Songs.class);
                    get_songs.getGmKey(dss.getKey());
                    currentindexartist=0;

                    if (s != null && s.equals(get_songs.getGsongCategory())||artnotify!=null&&artnotify.equals(get_songs.getGsongCategory())) {
                        list.add(get_songs);
                        Acheckin = true;
                        jcAudiosartist.add(JcAudio.createFromURL(get_songs.getGsongTitle(), get_songs.getGsonglink()));
                    }
                }

                recyclerViewartistsong.setAdapter(artistJcSongsAdapter);
                artistJcSongsAdapter.setAseletedPosition(0);
                artistJcSongsAdapter.notifyDataSetChanged();
                progressBarartist.setVisibility(View.GONE);
                if(Acheckin){
                    if(s!=null){
                        application.setArtnotify(s);
                    }
                    if(artnotify!=null){
                        application.setArtnotify(artnotify);
                    }
                    jcPlayerViewArtist.initPlaylist(jcAudiosartist,null);
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
                progressBarartist.setVisibility(View.GONE);
            }
        });

        if(application.isCommanurlenable()){
            relativeLayout.setVisibility(View.VISIBLE);
            String ii=application.getArturl();
            Picasso.get().load(ii).into(circleImageView);
        }


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
    public void  changeSelectedSong(int index){
        artistJcSongsAdapter.notifyItemChanged(artistJcSongsAdapter.getAseletedPosition());
        currentindexartist=index;
        artistJcSongsAdapter.setAseletedPosition(currentindexartist);
        artistJcSongsAdapter.notifyItemChanged(currentindexartist);
    }
}