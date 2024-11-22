package com.alban.gods;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Adapter.JcSongsAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.GetSongs;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class favoritesongs extends AppCompatActivity {
    ImageView se,backarrowfav;
    RecyclerView recyclerView;
    Context c=this;
    TextView txtnosongs;
    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1,pro2;

    private JcSongsAdapter adapter;
    float xDown=0,yDown=0;
    private DatabaseReference mDatabaseSong = null;
    private ArrayList<GetSongs> listFavourite = new ArrayList<>();
    private RecyclerView recyclerRecentView = null;
    RelativeLayout relativeLayout;
    CircleImageView circleImageView;
    CircleMenu circleMenu;
    ImageView imageViewcancelnav;
    TextView txttest,txttest2,txttest3;
    FirebaseUser firebaseUser;
    TextView txtemail;
    BottomSheetDialog bottomSheetDialog;


    RelativeLayout re,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    ImageView power;
    FirebaseAuth firebaseAuth;
    RelativeLayout relativeLayoutgmail;





    TextView emailfirst;
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
    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c,library.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favoritesongs);
        emailfirst=findViewById(R.id.emailfirstfav);
        power=findViewById(R.id.powerfav);
        play=findViewById(R.id.playiconfav);
        pause=findViewById(R.id.pauseiconfav);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        profilerelative=findViewById(R.id.profilefornavfav);
        backarrowfav=findViewById(R.id.backarrowfav);
        txtnosongs=findViewById(R.id.textnosongsfav);
        relativeLayout=findViewById(R.id.forlooplistfav);
        circleImageView=findViewById(R.id.imagviewdraglyricslistfav);
        h1=findViewById(R.id.favhome);
        l1=findViewById(R.id.favlib);
        p1=findViewById(R.id.favup);
        s1=findViewById(R.id.favse);
        pro1=findViewById(R.id.favlyrics);
        txtemail=findViewById(R.id.txtemailfav);
        re=findViewById(R.id.refav);
        helprelative=findViewById(R.id.Helpfornavfav);
        karaokerelative=findViewById(R.id.karaokefornavfav);
        chortchartrelative=findViewById(R.id.chordchartfornavfav);

        imageViewcancelnav=findViewById(R.id.closenavfav);
        txtemail.setText(firebaseUser.getEmail().toString());

        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);

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
        power.setOnClickListener(new View.OnClickListener() {
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
        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,Setting.class);
                startActivity(intent);
            }
        });

        imageViewcancelnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roate);
                imageViewcancelnav.startAnimation(animation3);
                re.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                re.startAnimation(animation2);
                profilerelative.startAnimation(animation2);
                helprelative.startAnimation(animation2);
                karaokerelative.startAnimation(animation2);
                chortchartrelative.startAnimation(animation2);



            }
        });
        if(MyApplication.application.isEnabledarkmode()){
            re.setBackgroundColor(0xff121212);
        }
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
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,library.class);
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
                                Intent intent=new Intent(c,LLogin.class);
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

        imageViewcancelnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roate);
                imageViewcancelnav.startAnimation(animation3);
                re.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                re.startAnimation(animation2);
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
        if(MyApplication.application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);
            String ii= MyApplication.application.getSongUrl();
            Picasso.get().load(ii).into(circleImageView);
        }

        backarrowfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,library.class);
                startActivity(intent);
            }
        });
        // RecylcerAdapter
        adapter = new JcSongsAdapter(getApplicationContext(), listFavourite, new  JcSongsAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int postion) {

                //changeSelectedSong(postion);

                /*jcPlayerView.playAudio(jcAudios.get(postion));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();*/



                Intent intent=new Intent(favoritesongs.this,playmusic.class);
                intent.putExtra(playmusic.KEY_SONG_TITLE,songs.getSongTitle());
                intent.putExtra(playmusic.KEY_SONG_LINK,songs.getSongLink());
                intent.putExtra(playmusic.KEY_SONG_CATEGORY,songs.getSongCategory());
                intent.putExtra(playmusic.KEY_MUSIC_TYPE, MusicType.Favourite.ordinal());
                startActivity(intent);
            }
        });


        // Setup Recycler View
        recyclerView=findViewById(R.id.favplaylist);
       /* LinearLayoutManager linearLayoutManager=new LinearLayoutManager(library.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        // Setup Recycler View recent

        // setup data
        mDatabaseSong = FirebaseDatabase.getInstance().getReference("songs");
        mDatabaseSong.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> listFaouriteId = PrefStorage.getInstance().allFavourite();
                ArrayList<GetSongs> listSongs = new ArrayList<>();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    listSongs.add(getSongs);
                }

                for (String songid: listFaouriteId) {
                    for (GetSongs getSongs: listSongs) {
                        try {
                            if (getSongs.getSongTitle() != null && songid.equals ("" + getSongs.getSongTitle().hashCode())) {
                                listFavourite.add(getSongs);
                                txtnosongs.setVisibility(View.INVISIBLE);

                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Log.v("Library","Library OnCreate");

    }
    public void showPopup(View v){
        re.setVisibility(View.VISIBLE);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        re.startAnimation(animation1);
        profilerelative.startAnimation(animation1);
        helprelative.startAnimation(animation1);
        karaokerelative.startAnimation(animation1);
        chortchartrelative.startAnimation(animation1);

    }
    }
