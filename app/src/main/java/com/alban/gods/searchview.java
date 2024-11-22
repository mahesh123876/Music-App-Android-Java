package com.alban.gods;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Adapter.ArtistRecycleViewAdapter;
import com.alban.gods.Adapter.GenereRecycleViewAdapter;
import com.alban.gods.Adapter.Genere_Upload;
import com.alban.gods.Adapter.JcSongsAdapter;
import com.alban.gods.Adapter.RecycleViewAdapter;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import Model.GetSongs;
import Model.Upload;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class searchview extends AppCompatActivity  {

    ImageView pro2,search,voice,playse,pausese;
    Context c=this;
    private RecyclerView recyclerView = null;
    private EditText editText = null;
    private JcSongsAdapter adapter;
    private DatabaseReference databaseReference;
    private JcPlayerView jcPlayerView;
    private ValueEventListener valueEventListener;
    RelativeLayout relativeLayout;
    private int currentIndex;
    TextView txt;
    private List<GetSongs> listSongs = null, searchList = null;
    private  ArrayList<JcAudio> jcAudios =  new ArrayList<>();

    BottomNavigationView bottomNavigationView;
    ImageView pause,play;



    RecyclerView genererecycleview;
    DatabaseReference Gmdatabase;
    private List<Genere_Upload> genere_uploadList;
    GenereRecycleViewAdapter Gadapter;
    TextView t1,t2,t3;
    private MyApplication application=null;



    RecyclerView recyclerViewR;
    RecycleViewAdapter adapterR;
    DatabaseReference mDatabaseR;
    private List<Upload> Ruploads;



    RecyclerView ArtistRecycleView;
    DatabaseReference Amdatabase;
    private List<Artist_Upload> artist_uploadList;
    ArtistRecycleViewAdapter artistRecycleViewAdapter;
    CircleImageView circleImageView;
    float xDown=0,yDown=0;
public static searchview searchview;
CircleMenu circleMenu;
    ImageView libimageViewcancelnav;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    BottomSheetDialog bottomSheetDialog;
    TextView setextemail;
    ImageView sepower;
    TextView emailfirstse;

    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1;
    ScrollView scrollView;
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        application = (MyApplication)getApplication();
        setContentView(R.layout.activity_searchview);
        searchview=this;
        play=findViewById(R.id.playiconsearch);
        pause=findViewById(R.id.pauseiconsearch);
        sepower=findViewById(R.id.sepower);
        scrollView=findViewById(R.id.scrollsearchview);
        emailfirstse=findViewById(R.id.emailfirstse);
        setextemail=findViewById(R.id.setxtemail);
        libre=findViewById(R.id.sere);
        profilerelative=findViewById(R.id.seprofilefornav);
        helprelative=findViewById(R.id.seHelpfornav);
        karaokerelative=findViewById(R.id.sekaraokefornav);
        chortchartrelative=findViewById(R.id.sechordchartfornav);

        libimageViewcancelnav=findViewById(R.id.seclosenav);
        t1=findViewById(R.id.txtRec);

        circleImageView=findViewById(R.id.imagviewdragse);
        relativeLayout=findViewById(R.id.forloopse);
        scrollView.setVerticalScrollBarEnabled(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);
        MyApplication.application.setSearch(true);
        sepower.setOnClickListener(new View.OnClickListener() {
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
        setextemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);
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
        String ii=application.getSongUrl();
        Picasso.get().load(ii).into(circleImageView);
        t2=findViewById(R.id.txtGene);
        t3=findViewById(R.id.txtart);
        h1=findViewById(R.id.semain);
        l1=findViewById(R.id.selib);
        p1=findViewById(R.id.seup);
        s1=findViewById(R.id.sese);
        pro1=findViewById(R.id.selyrics);
        ArtistRecycleView=findViewById(R.id.Artistrecycleview);
        LinearLayoutManager linearLayoutManagerAC=new LinearLayoutManager(searchview.this,LinearLayoutManager.HORIZONTAL,false);
        ArtistRecycleView.setHasFixedSize(true);
        ArtistRecycleView.setLayoutManager(linearLayoutManagerAC);
        artist_uploadList=new ArrayList<>();
        Amdatabase=FirebaseDatabase.getInstance().getReference("ARuploads");
        artistRecycleViewAdapter=new ArtistRecycleViewAdapter(c,artist_uploadList);
        ArtistRecycleView.setAdapter(artistRecycleViewAdapter);
        Amdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artist_uploadList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Artist_Upload artistUpload=dataSnapshot.getValue(Artist_Upload.class);
                    artist_uploadList.add(0,artistUpload);
                    artistRecycleViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,library.class);
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
                Intent intent=new Intent(c,profile.class);
                startActivity(intent);
            }
        });

        recyclerViewR=findViewById(R.id.recycleviewSR);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(searchview.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewR.setLayoutManager(linearLayoutManager1);
        Ruploads=new ArrayList<>();
        adapterR=new RecycleViewAdapter(this,Ruploads);
        mDatabaseR= FirebaseDatabase.getInstance().getReference("uploads");
        recyclerViewR.setAdapter(adapterR);
        mDatabaseR.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    Ruploads.add(0,upload);
                    Log.v("MainActivity","Upload value "+upload.getSongCategory()+", Url "+upload.getUrl());
                    adapterR.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                         }
        });
        LinearLayoutManager linearLayoutManagerGC=new LinearLayoutManager(searchview.this,LinearLayoutManager.HORIZONTAL,false);
        genererecycleview=findViewById(R.id.recycleviewGR);
        genererecycleview.setHasFixedSize(true);
        genererecycleview.setLayoutManager(linearLayoutManagerGC);
        genere_uploadList=new ArrayList<>();
        Gmdatabase=FirebaseDatabase.getInstance().getReference("GEuploads");
        Gadapter=new GenereRecycleViewAdapter(c,genere_uploadList);
        genererecycleview.setAdapter(Gadapter);
        Gmdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                genere_uploadList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Genere_Upload genereUpload=dataSnapshot.getValue(Genere_Upload.class);
                    genere_uploadList.add(0,genereUpload);
                    Gadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt=findViewById(R.id.txtk);
        voice=findViewById(R.id.voice);
        searchList = new ArrayList<>();
        listSongs = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSongs.clear();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    listSongs.add(getSongs);



                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchSong(charSequence.toString());
                scrollView.setVisibility(View.INVISIBLE);
                recyclerViewR.setVisibility(View.INVISIBLE);
                genererecycleview.setVisibility(View.INVISIBLE);
                ArtistRecycleView.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        jcPlayerView  = findViewById(R.id.jcplayer);
        // EditText
        editText = findViewById(R.id.activity_searchView_editText);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Search Song
                        searchSong(s.toString());
                        scrollView.setVisibility(View.INVISIBLE);
                        recyclerViewR.setVisibility(View.INVISIBLE);
                        genererecycleview.setVisibility(View.INVISIBLE);
                        ArtistRecycleView.setVisibility(View.INVISIBLE);
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

        // RecyclerView
        recyclerView = findViewById(R.id.activity_searchView_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JcSongsAdapter(getApplicationContext(), searchList, new  JcSongsAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int postion) {
                Intent intent=new Intent(c,playmusic.class);
                intent.putExtra(playmusic.KEY_SONG_TITLE,songs.getSongTitle());
                intent.putExtra(playmusic.KEY_SONG_LINK,songs.getSongLink());
                intent.putExtra(playmusic.KEY_SONG_CATEGORY,songs.getSongCategory());
                /*jcPlayerView.playAudio(jcAudios.get(postion));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();*/
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeakVoice(view);
            }
        });

        if(application.isEnabledarkmode()){
            libre.setBackgroundColor(0xff121212);
        }
        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,Setting.class);
                startActivity(intent);
            }
        });
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstse.setText(getgmail.toUpperCase());
    }
    private void SpeakVoice(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking");
        startActivityForResult(intent,114);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==114&&resultCode==RESULT_OK){
            txt.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }

    private void searchSong(String searchValue) {

        searchList.clear();
        String lwrSearchValue = searchValue.toLowerCase();
        for (GetSongs song: listSongs) {
            if (song.getSongTitle().toLowerCase().startsWith(lwrSearchValue)) {
                searchList.add(song);
            }
        }
        // Setup Adapter
        //adapter.setSeletedPosition(0);
        adapter.notifyDataSetChanged();
    }
    public void  changeSelectedSong(int index){
        adapter.notifyItemChanged(adapter.getSeletedPosition());
        currentIndex=index;
        adapter.setSeletedPosition(currentIndex);
        adapter.notifyItemChanged(currentIndex);
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
    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c,MainActivity.class);
        startActivity(intent);
    }


    public  void se(){
        pausese.setVisibility(View.INVISIBLE);
    }




}