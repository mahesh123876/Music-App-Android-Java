package com.alban.gods;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alban.gods.Adapter.ArtistSongsActivity;
import com.alban.gods.Adapter.GenereSongsActivity;
import com.alban.gods.Adapter.Genere_Get_Songs;
import com.alban.gods.Adapter.TrendingSongActivity;
import com.alban.gods.Adapter.Trending_Get_Songs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jean.jcplayer.JcPlayerManager;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.GetSongs;
import Model.SongsActivity;
import Model.Upload;
import pl.droidsonroids.gif.GifImageView;

public class playmusic extends AppCompatActivity implements JcPlayerManagerListener{

    public static String KEY_SONG_TITLE = "com.example.lcproclientapp.song_title";
    public static String KEY_SONG_LINK = "com.example.lcproclientapp.song_link";
    public static String KEY_SONG_CATEGORY = "com.example.lcproclientapp.song_category";
    public static String KEY_MUSIC_TYPE = "com.example.lcproclientapp.song.type";
    public static String KEY_MUSIC_POSITION = "com.example.lcproclientapp.song.position";
    public static playmusic playmusic;
    JcPlayerManager jcPlayerManager;
    private MyApplication application = null;
    private  ImageView imgView = null;
    private JcPlayerView jcPlayerView = null;
    ImageView fav1,fav2,h1,h2,l1,l2,p1,p2,s1,s2,pro1,pro2,playlist1,playlist2,downarrow,uparrow;
    private DatabaseReference mDatabase, mDatabaseSong;
    RelativeLayout relativeLayout;
    private String title, link;
    Button lybtn,songs,circle1,circle2,circle3,circle4;
    private ArrayList<JcAudio> listAudios = new ArrayList<>();
    private ArrayList<JcAudio> listAudiostrend = new ArrayList<>();
    private ArrayList<JcAudio> listAudiogenere = new ArrayList<>();
    private ArrayList<JcAudio> listAudioart = new ArrayList<>();
    private PrefStorage prefStorage = null;
    Intent intent;
    Context c=this;
    String jumma;
    String category=null;

    BottomSheetDialog bottomSheetDialog;
    RelativeLayout relativeLayoutxml;
    DatabaseReference databaseReferencetrending;

    DatabaseReference databaseReferencegenere;
    DatabaseReference databaseReferenceArt,databaseReferencese;

    String trendsongtitle;
    String trendsonglink;
    String trendcate;

    String generesongtitle;
    String generesonglink;
    String generecate;


    String searchsongtitle;
    String searchsonglink;
    String searchcate;


    String artsongtitle;
    String artsonglink;
    String artcate;


    ImageView speaker;
    SeekBar seekBar;
    NotificationChannel notificationChannel;

    GifImageView heartgif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication)getApplication();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_playmusic);
        playmusic=this;
        relativeLayoutxml=findViewById(R.id.xml);
        heartgif=findViewById(R.id.heartgif);
        circle1=findViewById(R.id.circle1);
        circle3=findViewById(R.id.circle3);
        circle2=findViewById(R.id.circle2);
        circle4=findViewById(R.id.circle4);
        seekBar=findViewById(R.id.volume);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottomdown);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        speaker=findViewById(R.id.volumeicon);
        songs=findViewById(R.id.btnsongs);
        fav1=findViewById(R.id.heart1);
        fav2=findViewById(R.id.heart2);
        downarrow=findViewById(R.id.downarrow);
        lybtn=findViewById(R.id.btnly);
        playlist1=findViewById(R.id.playlist1);
        playlist2=findViewById(R.id.playlist2);
        h1=findViewById(R.id.hpm);
        l1=findViewById(R.id.lpm);
        p1=findViewById(R.id.upm);
        s1=findViewById(R.id.spm);
        pro1=findViewById(R.id.ppm);

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekBar.setVisibility(View.VISIBLE);
                seekBar.startAnimation(animation3);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setVisibility(View.INVISIBLE);
                        seekBar.startAnimation(animation2);
                    }
                },4000);

            }
        });
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Set the maximum volume of the SeekBar to the maximum volume of the MediaPlayer:
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(maxVolume);

        // Set the current volume of the SeekBar to the current volume of the MediaPlayer:
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(currVolume);

        // Add a SeekBar.OnSeekBarChangeListener to the SeekBar:
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }
        });


        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application.isEnableart()){
                    Intent intent1=new Intent(c, ArtistSongsActivity.class);
                    intent1.putExtra("backa",application.getArtnotify());
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                }
                if(application.isEnablegenere()){
                    Intent intent1=new Intent(c, GenereSongsActivity.class);
                    intent1.putExtra("backg",application.getGenrenotify());
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "enable genere", Toast.LENGTH_SHORT).show();
                }
                if(application.isEnabletrend()){
                    Intent intent1=new Intent(c, MainActivity.class);
                    intent1.putExtra("backt",application.getTrendingnotify());
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "enable Trend", Toast.LENGTH_SHORT).show();
                }
                if(application.isEnablealbum()){
                    Intent intent1=new Intent(c,SongsActivity.class);
                    intent1.putExtra("back",application.getNotify());
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "back"+application.getNotify(), Toast.LENGTH_SHORT).show();
                }
                if(application.isEnablerecent()){
                    Intent intent1=new Intent(c,recentlyplayed.class);
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "enable genere", Toast.LENGTH_SHORT).show();
                }
                if(application.isEnablefav()){

                    Intent intent1=new Intent(c,favoritesongs.class);
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "enable Trend", Toast.LENGTH_SHORT).show();
                }
                if(application.isEnableplaylist()){
                    Intent intent1=new Intent(c,playlist.class);
                    startActivity(intent1);
                    Animatoo.animateSlideDown(c);
                    Toast.makeText(playmusic, "enable albums", Toast.LENGTH_SHORT).show();
                }
                if(application.isEnablesearchsong()){
                    Intent intent1=new Intent(c,searchview.class);
                    startActivity(intent1);
                }
                else{
                   System.out.println("helo");
                }

            }
        });
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,MainActivity.class);
                startActivity(intent);
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,library.class);
                startActivity(intent);
            }
        });

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,upload.class);
                startActivity(intent);
            }
        });

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,searchview.class);
                startActivity(intent);
            }
        });

        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(c,profile.class);
                intent.putExtra("title",title);
                intent.putExtra("link",link);
                startActivity(intent);
            }
        });


        playlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlist1.setVisibility(View.INVISIBLE);
                playlist2.setVisibility(View.VISIBLE);
                Toast.makeText(c, "PLAYLIST ADDED", Toast.LENGTH_SHORT).show();
                setEnablePlaylist(true);
            }
        });
        playlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlist1.setVisibility(View.VISIBLE);
                playlist2.setVisibility(View.INVISIBLE);
                setEnablePlaylist(false);
            }
        });
        lybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,profile.class);
                startActivity(intent);
            }
        });
        fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav1.setVisibility(View.INVISIBLE);
                heartgif.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        heartgif.setVisibility(View.INVISIBLE);
                        fav2.setVisibility(View.VISIBLE);
                        setEnableFavourite(true);
                        Toast.makeText(playmusic.this, "Song is added to Favotite", Toast.LENGTH_SHORT).show();
                    }
                },3000);

            }
        });
        fav2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fav1.setVisibility(View.VISIBLE);
                fav2.setVisibility(View.INVISIBLE);
                setEnableFavourite(false);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,library.class);
                startActivity(intent);
            }
        });
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,searchview.class);
                startActivity(intent);
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,upload.class);
                startActivity(intent);
            }
        });

        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(c,MainActivity.class);
                startActivity(intent);

            }
        });

        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c,profile.class);
                startActivity(intent);
            }
        });

        trendsongtitle=getIntent().getStringExtra("trendingsongtitle");
        trendsonglink=getIntent().getStringExtra("trendingsonglink");
        trendcate=getIntent().getStringExtra("trendingsongcate");


        generesongtitle=getIntent().getStringExtra("generesongtitle");
        generesonglink=getIntent().getStringExtra("generesonglink");
        generecate=getIntent().getStringExtra("generesongcate");


        artsongtitle=getIntent().getStringExtra("Artsongtitle");
        artsonglink=getIntent().getStringExtra("Artsonglink");
        artcate=getIntent().getStringExtra("Artsongcate");

        searchsongtitle=getIntent().getStringExtra(com.alban.gods.playmusic.KEY_SONG_TITLE);
        searchsonglink=getIntent().getStringExtra(com.alban.gods.playmusic.KEY_SONG_LINK);
        searchcate=getIntent().getStringExtra(com.alban.gods.playmusic.KEY_SONG_CATEGORY);





        // ImageView
        imgView = findViewById(R.id.activity_playMusic_imgView_album_art);
        // JCPlayerView
        jcPlayerView = findViewById(R.id.activityy_playmusic_jcPlayerView);
        jcPlayerView.setJcPlayerManagerListener(this);
        title = getIntent().getStringExtra(KEY_SONG_TITLE);
        link = getIntent().getStringExtra(KEY_SONG_LINK);
        //listAudios.add(JcAudio.createFromURL(title,link));
        //jcPlayerView.initPlaylist(listAudios,null);
        prefStorage = PrefStorage.getInstance();
        category = getIntent().getStringExtra(KEY_SONG_CATEGORY);
        mDatabase= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String albumUrl = "";
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    if (upload.getSongCategory().equals(category)) {
                        albumUrl = upload.getUrl();
                        break;
                    }
                }
                if (albumUrl != null && !albumUrl.isEmpty()) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.nothing).error(R.mipmap.ic_launcher);
                    Glide.with(playmusic.this).load(albumUrl).apply(requestOptions).into(imgView);
                    application.setSongUrl(albumUrl);
                    application.setSongCircle(true);
                    application.setCommanurlenable(true);
                    application.setArturl(albumUrl);

                }
                //play();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if(trendsongtitle!=null&&trendsonglink!=null&&trendcate!=null) {
            String gettrendyrl=MyApplication.application.getSongtrendingurl();
            Picasso.get().load(gettrendyrl).into(imgView);
            application.setSongCircle(true);
            application.setCommanurlenable(true);
            application.setArturl(gettrendyrl);
            application.setSongUrl(gettrendyrl);
            application.setEnabletrend(true);
            application.setEnablegenere(false);
            application.setEnablealbum(false);
            application.setEnablefav(false);
            application.setEnablerecent(false);
            application.setEnableplaylist(false);
            application.setEnableart(false);

            databaseReferencetrending = FirebaseDatabase.getInstance().getReference("Trending");
            databaseReferencetrending.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int indextrend = 0;
                    ArrayList<Trending_Get_Songs> listSongstrend = new ArrayList<>();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Trending_Get_Songs trendingGetSongs = dss.getValue(Trending_Get_Songs.class);
                        trendingGetSongs.setmKey(dss.getKey());
                        listSongstrend.add(trendingGetSongs);
                    }
                    for (Trending_Get_Songs getSongstrend : listSongstrend) {
                        //final String s = getIntent().getStringExtra("songCategory");
                        if (trendcate.equals(getSongstrend.getSongCategory())) {
                            listAudiostrend.add(JcAudio.createFromURL(getSongstrend.getSongTitle(), getSongstrend.getSonglink()));
                            if (trendsongtitle != null && trendsongtitle.equals(getSongstrend.getSongTitle()) &&
                                    trendsonglink != null && trendsonglink.equals(getSongstrend.getSonglink())) {
                                indextrend = listAudiostrend.size() - 1;

                            }
                        }
                    }
                    jcPlayerView.initPlaylist(listAudiostrend,null);
                    playtrend(indextrend);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        if(artsongtitle!=null&&artsonglink!=null&&artcate!=null){
            String getarturl=MyApplication.application.getArturlmain();
            Picasso.get().load(getarturl).into(imgView);
            application.setSongCircle(true);
            application.setSongUrl(getarturl);
            application.setEnablegenere(false);
            application.setEnabletrend(false);
            application.setEnablealbum(false);
            application.setEnablefav(false);
            application.setEnablerecent(false);
            application.setEnableplaylist(false);
            application.setCommanurlenable(true);
            application.setArturl(getarturl);
            application.setEnableart(true);
            databaseReferenceArt= FirebaseDatabase.getInstance().getReference("songs");
            databaseReferenceArt.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int Artindex=0;
                    ArrayList<Artist_Get_Songs> artist_get_songs=new ArrayList<>();
                    for (DataSnapshot dss: snapshot.getChildren()) {
                        Artist_Get_Songs art=dss.getValue(Artist_Get_Songs.class);
                        art.setGmKey(dss.getKey());
                        artist_get_songs.add(art);
                    }
                    for (Artist_Get_Songs artistGetSongs: artist_get_songs) {
                        if(artcate.equals(artistGetSongs.getGsongCategory())){
                         listAudioart.add(JcAudio.createFromURL(artistGetSongs.getGsongTitle(),artistGetSongs.getGsonglink()));
                      if(artsongtitle!=null&&artsongtitle.equals(artistGetSongs.getGsongTitle())&&artsonglink!=null&&artsonglink.equals(artistGetSongs.getGsonglink())){
                          Artindex = listAudioart.size() - 1;
                      }

                        }
                    }
                    jcPlayerView.initPlaylist(listAudioart,null);
                    playArt(Artindex);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        else if(generesongtitle!=null&&generesonglink!=null&&generecate!=null){
            String getgenereyrl=MyApplication.application.getSonggenereurl();
            Picasso.get().load(getgenereyrl).into(imgView);
            application.setSongCircle(true);
            application.setSongUrl(getgenereyrl);
            application.setEnablegenere(true);
            application.setEnabletrend(false);
            application.setEnablealbum(false);
            application.setEnablefav(false);
            application.setEnablerecent(false);
            application.setEnableplaylist(false);
            application.setCommanurlenable(true);
            application.setArturl(getgenereyrl);
            application.setEnableart(false);
            databaseReferencegenere= FirebaseDatabase.getInstance().getReference("Genere");
            databaseReferencegenere.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int indextgenere= 0;
                    ArrayList<Genere_Get_Songs> listSongsgenere = new ArrayList<>();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Genere_Get_Songs genereGetSongs = dss.getValue(Genere_Get_Songs.class);
                        genereGetSongs.getGmKey(dss.getKey());
                        listSongsgenere.add(genereGetSongs);
                    }
                    for (Genere_Get_Songs getSongsGenere : listSongsgenere) {
                        //final String s = getIntent().getStringExtra("songCategory");
                        if (generecate.equals(getSongsGenere.getGsongCategory())){
                            listAudiogenere.add(JcAudio.createFromURL(getSongsGenere.getGsongTitle(), getSongsGenere.getGsonglink()));
                            if (generesongtitle != null && generesongtitle.equals(getSongsGenere.getGsongTitle()) &&
                                    trendsonglink != null && trendsonglink.equals(getSongsGenere.getGsonglink())) {
                                indextgenere = listAudiogenere.size() - 1;
                            }
                        }
                    }
                    jcPlayerView.initPlaylist(listAudiogenere,null);
                    playgenere(indextgenere);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(title!=null&&link!=null&&category!=null){
            application.setEnablealbum(true);
            application.setEnabletrend(false);
            application.setEnablefav(false);
            application.setEnablerecent(false);
            application.setEnableplaylist(false);
            application.setEnablegenere(false);
            application.setEnableart(false);
            application.setEnablesearchsong(true);
            mDatabaseSong = FirebaseDatabase.getInstance().getReference("songs");
            mDatabaseSong.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int index = 0;
                    ArrayList<GetSongs> listSongs = new ArrayList<>();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        GetSongs getSongs = dss.getValue(GetSongs.class);
                        getSongs.setmKey(dss.getKey());
                        listSongs.add(getSongs);
                    }
                    MusicType mType = MusicType.values()[getIntent().getIntExtra(KEY_MUSIC_TYPE, MusicType.Category.ordinal())];
                    if (mType == MusicType.Category) {

                        for (GetSongs getSongs : listSongs) {
                            //final String s = getIntent().getStringExtra("songCategory");
                            Log.v("PlayMusic", "1 " + category + ", " + getSongs.getSongCategory());
                            if (category.equals(getSongs.getSongCategory())) {
                                listAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                                if (title != null && title.equals(getSongs.getSongTitle()) && link != null && link.equals(getSongs.getSongLink())) {
                                    index = listAudios.size() - 1;
                                }
                            }
                        }
                    }
                    else if (mType == MusicType.Recent) {
                        application.setEnablegenere(false);
                        application.setEnabletrend(false);
                        application.setEnablealbum(false);
                        application.setEnablefav(false);
                        application.setEnablerecent(true);
                        application.setEnableplaylist(false);
                        application.setEnableart(false);

                        ArrayList<String> recents =  PrefStorage.getInstance().allRecent();
                        for (String songid: recents) {
                            for (GetSongs getSongs: listSongs) {
                                if (songid.equals(""+getSongs.getSongTitle().hashCode())) {
                                    listAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                                    if (title != null && title.equals(getSongs.getSongTitle()) &&
                                            link != null && link.equals(getSongs.getSongLink())) {
                                        index = listAudios.size() - 1;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    else if (mType == MusicType.PlayList) {
                        application.setEnablerecent(false);
                        application.setEnablegenere(false);
                        application.setEnabletrend(false);
                        application.setEnablealbum(false);
                        application.setEnablefav(false);
                        application.setEnableplaylist(true);
                        application.setEnableart(false);
                        ArrayList<String> playList = PrefStorage.getInstance().allPlaylist();
                        for (String songid: playList) {
                            for (GetSongs getSongs: listSongs) {
                                if (songid.equals(""+getSongs.getSongTitle().hashCode())) {
                                    listAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                                    if (title != null && title.equals(getSongs.getSongTitle()) &&
                                            link != null && link.equals(getSongs.getSongLink())) {
                                        index = listAudios.size() - 1;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    else if (mType == MusicType.Favourite) {
                        application.setEnablerecent(false);
                        application.setEnablegenere(false);
                        application.setEnabletrend(false);
                        application.setEnablealbum(false);
                        application.setEnablefav(true);
                        application.setEnableplaylist(false);
                        application.setEnableart(false);

                        ArrayList<String> listFavouriteId = PrefStorage.getInstance().allFavourite();
                        for (String songid: listFavouriteId) {
                            for (GetSongs getSongs: listSongs) {
                                if (songid.equals(""+getSongs.getSongTitle().hashCode())) {
                                    listAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                                    if (title != null && title.equals(getSongs.getSongTitle()) &&
                                            link != null && link.equals(getSongs.getSongLink())) {
                                        index = listAudios.size() - 1;
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    //listAudios.add(0,JcAudio.createFromURL(title,link));
                    jcPlayerView.initPlaylist(listAudios,null);
                    //playSong();
                    play(index);


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void playgenere(int index) {
        JcAudio audio = listAudiogenere.get(index);
        jcPlayerView.playAudio(audio);
        jcPlayerView.createNotification(R.drawable.notification);
    }


    private void playArt(int index) {


        JcAudio audio = listAudioart.get(index);
        jcPlayerView.playAudio(audio);
        jcPlayerView.createNotification(R.drawable.notification);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Intent intent = new Intent();
            intent.setAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
            sendBroadcast(intent);
        } catch(Exception e){

        }
    }
    public  void  current(){
     jcPlayerView.pause();
    }
    public  void playsongs(){
        jcPlayerView.continueAudio();
    }

    private void playSong() {

        jcPlayerView.playAudio(JcAudio.createFromURL(title,link));
        jcPlayerView.createNotification(R.drawable.notification);

    }



    private void play(int index) {


        long pos = getIntent().getLongExtra(KEY_MUSIC_POSITION, -1);
        Log.v("PlayMusic","Current pos"+pos);
        JcAudio audio = listAudios.get(index);
        if (pos == -1) {
            jcPlayerView.playAudio(audio);
            jcPlayerView.createNotification(R.drawable.notification);
            application.setActiveSongId(""+audio.getTitle().hashCode());
        } else {
            //jcPlayerView.continueAudio();
            jcPlayerView.resume();
        }
    }
    private void playtrend(int index) {

        JcAudio audio = listAudiostrend.get(index);
            jcPlayerView.playAudio(audio);
            jcPlayerView.createNotification(R.drawable.notification);
    }

    private void setEnableFavourite(boolean value) {
        String songId = ""+jcPlayerView.getCurrentAudio().getTitle().hashCode();
        Log.v("playmusic","onprepared Enable "+songId);
        if (value) {
            prefStorage.addToFavourite(songId);
        } else {
            prefStorage.removeFromFavourite(songId);
        }
    }
    private void setEnablePlaylist(boolean value) {
        String songId = ""+jcPlayerView.getCurrentAudio().getTitle().hashCode();
        Log.v("playmusic","onprepared Enable "+songId);
        if (value) {
            prefStorage.addToPlaylist(songId);
        } else {
            prefStorage.removeFromPlaylist(songId);
        }
    }

    private void setupFavouriteView(boolean value) {
        if (value){
            fav1.setVisibility(View.INVISIBLE);
            fav2.setVisibility(View.VISIBLE);
        } else {
            fav1.setVisibility(View.VISIBLE);
            fav2.setVisibility(View.INVISIBLE);
        }
    }
    private void setupPlaylistView(boolean value) {
        if (value){
            playlist1.setVisibility(View.INVISIBLE);
            playlist2.setVisibility(View.VISIBLE);
        } else {
            playlist1.setVisibility(View.VISIBLE);
            playlist2.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onCompletedAudio() {
        Log.v("Plamusic","Completed");
    }

    @Override
    public void onContinueAudio(@NonNull JcStatus jcStatus) {
        Log.v("plamusic","plam ContinueAudio");




    }

    @Override
    public void onJcpError(@NonNull Throwable throwable) {
        Log.v("Plamusic","onJcpError");
    }

    @Override
    public void onPaused(@NonNull JcStatus jcStatus) {
        Log.v("Plamusic","plam Pasused");



    }

    @Override
    public void onPlaying(@NonNull JcStatus jcStatus) {
        Log.v("Plamusic","Playing");


    }

    @Override
    public void onPreparedAudio(@NonNull JcStatus jcStatus){
        String songId = ""+jcStatus.getJcAudio().getTitle().hashCode();
        Log.v("Plamusic","onprepared "+ songId);
        setupFavouriteView(prefStorage.isFavourite(songId));
        setupPlaylistView(prefStorage.isPLaylist(songId));
        prefStorage.addToRecent(songId);
        application.setActiveSongId(songId);
    }

    @Override
    public void onStopped(@NonNull JcStatus jcStatus) {
        Log.v("Plamusic","plam Stopped");
    }

    @Override
    public void onTimeChanged(@NonNull JcStatus jcStatus) {
        Log.v("Plamusic","plam TimeChanged");
    }

    @Override
    public void onBackPressed() {
        if(application.isEnableart()){
            Intent intent1=new Intent(c,ArtistSongsActivity.class);
            intent1.putExtra("backa",application.getArtnotify());
            startActivity(intent1);
        }
         if(application.isEnablegenere()){
            Intent intent1=new Intent(c, GenereSongsActivity.class);
            intent1.putExtra("backg",application.getGenrenotify());
            startActivity(intent1);
        }
         if(application.isEnabletrend()){
            Intent intent1=new Intent(c,MainActivity.class);
            intent1.putExtra("backt",application.getTrendingnotify());
            startActivity(intent1);
        }
         if(application.isEnablealbum()){
            Intent intent1=new Intent(c,SongsActivity.class);
            intent1.putExtra("back",application.getNotify());
            startActivity(intent1);
        }
         if(application.isEnablesearchsong()){
            Intent intent1=new Intent(c,searchview.class);
            startActivity(intent1);
        }
         if(application.isEnablerecent()){
            Intent intent1=new Intent(c,recentlyplayed.class);
            startActivity(intent1);
        }
         if(application.isEnablefav()){
            Intent intent1=new Intent(c,favoritesongs.class);
            startActivity(intent1);
        }
         if(application.isEnableplaylist()){
            Intent intent1=new Intent(c,playlist.class);
            startActivity(intent1);
        }
        else{
            System.out.println("hleo");
        }

    }
}