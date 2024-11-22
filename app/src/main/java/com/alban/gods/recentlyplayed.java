package com.alban.gods;



import static com.alban.gods.MyApplication.application;
import static com.alban.gods.PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE;

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

public class recentlyplayed extends AppCompatActivity {
    ImageView pro2,se,backarrowrecent;
    private DatabaseReference mDatabaseSong = null;
    private RecyclerView recyclerRecentView = null;
    private JcSongsAdapter adapter = null;
    TextView txtno;
    private ArrayList<GetSongs> listRecent = new ArrayList<>();
    Context c=this;
    RelativeLayout relativeLayout;
    CircleImageView circleImageView;
    float xDown=0,yDown=0;
    CircleMenu circleMenu;
    ImageView imageViewcancelnav;
    TextView txttest,txttest2,txttest3;
    FirebaseUser firebaseUser;
    TextView txtemail;
    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1;


    RelativeLayout re,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    ImageView power;
    FirebaseAuth firebaseAuth;
    RelativeLayout relativeLayoutgmail;





    TextView emailfirst;
    BottomSheetDialog bottomSheetDialog;
    ImageView pause,play;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            switch (action) {
                case ACTION_UPDATE_CATEGORY_IMAGE:

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
        setContentView(R.layout.activity_recentlyplayed);
        emailfirst=findViewById(R.id.emailfirstre);
        power=findViewById(R.id.powerre);
        play=findViewById(R.id.playiconrec);
        pause=findViewById(R.id.pauseiconrec);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());
        profilerelative=findViewById(R.id.profilefornavre);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerRecentView = findViewById(R.id.recyclerViewRecent);
        backarrowrecent=findViewById(R.id.recentbackarrow);
        txtno=findViewById(R.id.nosongsrecent);
        circleImageView=findViewById(R.id.imagviewdraglyricslistrec);
        relativeLayout=findViewById(R.id.forlooplistrec);

        h1=findViewById(R.id.rechome);
        l1=findViewById(R.id.reclib);
        p1=findViewById(R.id.recup);
        s1=findViewById(R.id.recse);
        pro1=findViewById(R.id.reclyrics);
        txtemail=findViewById(R.id.txtemailre);

        re=findViewById(R.id.rere);
        helprelative=findViewById(R.id.Helpfornavre);
        karaokerelative=findViewById(R.id.karaokefornavre);
        chortchartrelative=findViewById(R.id.chordchartfornavre);
        imageViewcancelnav=findViewById(R.id.closenavre);
        txtemail.setText(firebaseUser.getEmail().toString());

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_CATEGORY_IMAGE);
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
        if(application.isEnabledarkmode()){
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
        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);
            String ii=application.getSongUrl();
            Picasso.get().load(ii).into(circleImageView);
        }


        backarrowrecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,library.class);
                startActivity(intent);
            }
        });

        recyclerRecentView.setHasFixedSize(true);
        recyclerRecentView.setLayoutManager(new LinearLayoutManager(this));

        // Recylcer Adapter
        adapter = new JcSongsAdapter(getApplicationContext(), listRecent, new  JcSongsAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int postion) {

                //changeSelectedSong(postion);

                /*jcPlayerView.playAudio(jcAudios.get(postion));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();*/

                Intent intent=new Intent(recentlyplayed.this,playmusic.class);
                intent.putExtra(playmusic.KEY_SONG_TITLE,songs.getSongTitle());
                intent.putExtra(playmusic.KEY_SONG_LINK,songs.getSongLink());
                intent.putExtra(playmusic.KEY_SONG_CATEGORY,songs.getSongCategory());
                intent.putExtra(playmusic.KEY_MUSIC_TYPE, MusicType.Recent.ordinal());
                startActivity(intent);
            }
        });

        // setup data
        mDatabaseSong = FirebaseDatabase.getInstance().getReference("songs");
        mDatabaseSong.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> listRecentId = PrefStorage.getInstance().allRecent();
                Log.v("Library","Library recent "+listRecentId.size());
                /*for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());

                    try {
                        if (getSongs.getSongTitle() != null && listRecentId.contains(""+getSongs.getSongTitle().hashCode())) {
                            listRecent.add(getSongs);
                        }
                    } catch (Exception ex) {

                    }
                }*/
                ArrayList<GetSongs> listSongs = new ArrayList<>();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    listSongs.add(getSongs);
                }

                for (String songid: listRecentId ) {
                    for (GetSongs gs: listSongs) {
                        if (gs.getSongTitle() != null && songid.equals(""+gs.getSongTitle().hashCode())) {
                            listRecent.add(gs);
                            txtno.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                }

                // Set Recent
                recyclerRecentView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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