package com.alban.gods;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import Model.GetSongs;
import Model.Upload;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class library extends AppCompatActivity  {

    ImageView pro2,se,arrowrecent,playlib,pauselib,arrowplaylist,arrowfav,backarrowlib;
    Context c=this;
    LinearLayout  h1,s1,p1, l1,pro1;
    BottomNavigationView bottomNavigationView;

    private PrefStorage prefStorage = null;
    private DatabaseReference mDatabase, mDatabaseSong;
    JcPlayerView jcPlayerView;
    private CircleImageView imgViewRecentOne, imgViewRecentTwo, imgViewrecentThree,imgViewplaylistOne, imgViewplaylistTwo, imgViewplaylistThree;
    private ArrayList<GetSongs> recents = new ArrayList<>();
    private ArrayList<String> recentIds;
    private ArrayList<GetSongs> favorites = new ArrayList<>();
    private ArrayList<String> favoriteIds;
    private ArrayList<GetSongs> playlists = new ArrayList<>();
    private ArrayList<String> playlistIds;
    CircleImageView circleImageView;
    float xDown=0,yDown=0;
    private MyApplication application=null;
    RelativeLayout relativeLayout;
    public  static  library library;
    CircleImageView imgViewfavOne, imgViewfavTwo, imgViewfavThree;
    ImageView libimageViewcancelnav;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    BottomSheetDialog bottomSheetDialog;
    TextView textViewemail;
    ImageView libpower;
    TextView emailfirstlib;
    ScrollView scrollView;
    ImageView pause,play;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            switch (action) {
                case PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE:

                    finish();
                    break;
            }
        }
    };
    HorizontalScrollView horizontalScrollViewfav,horizontalScrollViewrecent,horizontalScrollViewplaylist;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        application = (MyApplication)getApplication();
        setContentView(R.layout.activity_library);
        play=findViewById(R.id.playiconlib);
        pause=findViewById(R.id.pauseiconlib);
        libpower=findViewById(R.id.libpower);
        emailfirstlib=findViewById(R.id.emailfirstlib);
        scrollView=findViewById(R.id.libmainscroll);
        library=this;

        textViewemail=findViewById(R.id.libtxtemail);
        libre=findViewById(R.id.libre);
        profilerelative=findViewById(R.id.libprofilefornav);
        helprelative=findViewById(R.id.libHelpfornav);
        karaokerelative=findViewById(R.id.libkaraokefornav);
        chortchartrelative=findViewById(R.id.libchordchartfornav);
        horizontalScrollViewfav=findViewById(R.id.horizontalscroolfav);
        horizontalScrollViewrecent=findViewById(R.id.horizontalrecentplayed);
        horizontalScrollViewplaylist=findViewById(R.id.horizontalscroolplaylist);
        libimageViewcancelnav=findViewById(R.id.libclosenav);
        circleImageView=findViewById(R.id.imagviewdrag);
        relativeLayout=findViewById(R.id.forlooplib);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstlib.setText(getgmail.toUpperCase());
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);
        horizontalScrollViewfav.setHorizontalScrollBarEnabled(false);
        horizontalScrollViewrecent.setHorizontalScrollBarEnabled(false);
        horizontalScrollViewplaylist.setHorizontalScrollBarEnabled(false);
        scrollView.setVerticalScrollBarEnabled(false);



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

        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,Setting.class);
                startActivity(intent);
            }
        });
        if(application.isEnabledarkmode()){
            libre.setBackgroundColor(0xff121212);
        }


        libpower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog=new BottomSheetDialog(c,R.style.BottomSheetStyle);
                View view=LayoutInflater.from(c).inflate(R.layout.logoutbottom,(RelativeLayout)findViewById(R.id.layoutforloutbottom));
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
                                Intent intent=new Intent(c,LLogin.class);
                                startActivity(intent);
                            }
                        },1400);


                    }
                });
            }
        });
        textViewemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);

        }
        karaokerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,karaoke.class);
                startActivity(intent);

            }
        });
        chortchartrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,chart.class);
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


        String ii=application.getSongUrl();
        Picasso.get().load(ii).into(circleImageView);
        h1=findViewById(R.id.hmainactlib);
        l1=findViewById(R.id.lmainactlib);
        p1=findViewById(R.id.umainactlib);
        s1=findViewById(R.id.smainactlib);
        pro1=findViewById(R.id.pmainactlib);

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,MainActivity.class);
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
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,profile.class);
                startActivity(intent);
            }
        });

        imgViewplaylistOne=findViewById(R.id.activity_library_playlist_one);
        imgViewplaylistTwo=findViewById(R.id.activity_library_playlist_two);
        imgViewplaylistThree=findViewById(R.id.activity_library_playlist_three);
        imgViewfavOne=findViewById(R.id.activity_library_fav_one);
        imgViewfavTwo=findViewById(R.id.activity_library_fav_two);
        imgViewfavThree=findViewById(R.id.activity_library_fav_three);

        arrowfav=findViewById(R.id.favarrow);
        arrowplaylist=findViewById(R.id.playlistarrow);
        arrowrecent=findViewById(R.id.recentarrow);
        backarrowlib=findViewById(R.id.backarrowlib);
        jcPlayerView = findViewById(R.id.jcplayer);
        backarrowlib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,MainActivity.class);
                startActivity(intent);
            }
        });
        arrowrecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in=new Intent(c,recentlyplayed.class);
                startActivity(in);

            }
        });
        arrowplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,playlist.class);
                startActivity(intent);

            }
        });
        arrowfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,favoritesongs.class);
                startActivity(intent);

            }
        });


        prefStorage = PrefStorage.getInstance();
        recentIds = prefStorage.allRecent();

        favoriteIds = prefStorage.allFavourite();
        for (String fid:favoriteIds) {
            Log.v("Library","Fav "+fid);
        }
        Collections.reverse(favoriteIds);
        Log.v("Library","Fav Reverse");
        for (String fid:favoriteIds) {
            Log.v("Library","Fav "+fid);
        }

        playlistIds = prefStorage.allPlaylist();
        Collections.reverse(playlistIds);
        imgViewRecentOne = findViewById(R.id.activity_library_recent_one);
        imgViewRecentTwo = findViewById(R.id.activity_library_recent_two);
        imgViewrecentThree = findViewById(R.id.activity_libary_recent_three);
        imgViewRecentOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,recentlyplayed.class);
                startActivity(intent);

            }
        });
        imgViewRecentTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,recentlyplayed.class);
                startActivity(intent);

            }
        });
        imgViewrecentThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,recentlyplayed.class);
                startActivity(intent);

            }
        });
        imgViewfavOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,favoritesongs.class);
                startActivity(intent);

            }
        });
        imgViewfavTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,favoritesongs.class);
                startActivity(intent);

            }
        });
        imgViewfavThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,favoritesongs.class);
                startActivity(intent);

            }
        });
        imgViewplaylistOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,playlist.class);
                startActivity(intent);

            }
        });
        imgViewplaylistTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,playlist.class);
                startActivity(intent);

            }
        });
        imgViewplaylistThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,playlist.class);
                startActivity(intent);

            }
        });
        mDatabaseSong = FirebaseDatabase.getInstance().getReference("songs");
        mDatabaseSong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<GetSongs> listSongs = new ArrayList<>();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    listSongs.add(getSongs);
                }

                int i = 1;
                for (String songid: recentIds ) {
                    for (GetSongs gSong: listSongs
                         ) {
                        if (songid.equals(""+gSong.getSongTitle().hashCode())) {
                            recents.add(gSong);
                            break;
                        }
                    }
                    if (i == 3) {
                        break;
                    }
                    i++;
                }
                i = 1;
                for (String songid: favoriteIds ) {
                    for (GetSongs gSong: listSongs
                    ) {
                        if (songid.equals(""+gSong.getSongTitle().hashCode())) {
                            favorites.add(gSong);
                            Log.v("Library","Fav "+i+" Songid "+songid);
                            break;
                        }
                    }
                    if (i == 3) {
                        break;
                    }
                    i++;
                }
                i = 1;
                for (String songid: playlistIds ) {
                    for (GetSongs gSong: listSongs
                    ) {
                        if (songid.equals(""+gSong.getSongTitle().hashCode())) {
                            playlists.add(gSong);
                            break;
                        }
                    }
                    if (i == 3) {
                        break;
                    }
                    i++;
                }
                loadUploads();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void loadUploads() {
        mDatabase= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String albumUrl = "";
                int i = 1;
                for (GetSongs gSong: recents) {
                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postsnapshot.getValue(Upload.class);
                        if (gSong.getSongCategory().equals(upload.getSongCategory())) {
                            albumUrl = upload.getUrl();
                            CircleImageView imageView;
                            switch (i) {

                                case 1:
                                    imageView = imgViewRecentOne;
                                    break;
                                case 2:
                                    imageView = imgViewRecentTwo;
                                    break;
                                default:
                                    imageView = imgViewrecentThree;
                            }
                            if (albumUrl != null && !albumUrl.isEmpty()) {
                                RequestOptions requestOptions = new RequestOptions();
                                requestOptions.placeholder(R.drawable.tune).error(R.drawable.tune);
                                Glide.with(library.this).load(albumUrl).apply(requestOptions).into(imageView);
                            }

                            break;
                        }
                    }
                    i++;
                }
                i = 1;
                for (GetSongs gSong: favorites
                ) {
                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postsnapshot.getValue(Upload.class);
                        if (gSong.getSongCategory().equals(upload.getSongCategory())) {
                            albumUrl = upload.getUrl();
                            CircleImageView imageViewfav;
                            switch (i) {
                                case 1:
                                    imageViewfav = imgViewfavOne;
                                    break;
                                case 2:
                                    imageViewfav = imgViewfavTwo;
                                    break;
                                default:
                                    imageViewfav = imgViewfavThree;
                            }
                            if (albumUrl != null && !albumUrl.isEmpty()) {
                                RequestOptions requestOptions = new RequestOptions();
                                requestOptions.placeholder(R.drawable.tune).error(R.drawable.tune);
                                Glide.with(library.this).load(albumUrl).apply(requestOptions).into(imageViewfav);
                            }
                            break;
                        }
                    }
                    i++;
                }
                i = 1;
                for (GetSongs gSong: playlists
                ) {
                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                        Upload upload = postsnapshot.getValue(Upload.class);
                        if (gSong.getSongCategory().equals(upload.getSongCategory())) {
                            albumUrl = upload.getUrl();
                            CircleImageView imageViewplaylist;
                            switch (i) {
                                case 1:
                                    imageViewplaylist = imgViewplaylistOne;
                                    break;
                                case 2:
                                    imageViewplaylist = imgViewplaylistTwo;
                                    break;
                                default:
                                    imageViewplaylist = imgViewplaylistThree;
                            }
                            if (albumUrl != null && !albumUrl.isEmpty()) {
                                RequestOptions requestOptions = new RequestOptions();
                                requestOptions.placeholder(R.drawable.tune).error(R.drawable.tune);
                                Glide.with(library.this).load(albumUrl).apply(requestOptions).into(imageViewplaylist);
                            }
                            break;
                        }
                    }
                    i++;
                }
                //play();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {

        }
    }




    public void lib(){
        pauselib.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c,MainActivity.class);
        startActivity(intent);
    }

}