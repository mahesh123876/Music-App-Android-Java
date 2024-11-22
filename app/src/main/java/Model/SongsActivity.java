package Model;



import static com.alban.gods.MyApplication.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.alban.gods.Adapter.PlayerReceiver;
import com.alban.gods.Adapter.RecyclerviewInterface;
import com.alban.gods.LLogin;
import com.alban.gods.MainActivity;
import com.alban.gods.MyApplication;
import com.alban.gods.PrefStorage;
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
import com.example.jean.jcplayer.service.notification.JcNotificationPlayer;
import com.example.jean.jcplayer.view.JcPlayerView;

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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class SongsActivity   extends AppCompatActivity {

    private MyApplication myApplication;
    JcNotificationPlayer jcNotificationPlayer;

    RecyclerView recyclerView;
    boolean checkin = false;
    String s;
    GifImageView g;
    List<GetSongs>mupload;
    public static String KEY_SONG_CATEGORY = "com.example.lcproclientapp.song_category";
    JcSongsAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios =  new ArrayList<>();
    private int currentIndex;
    CircleImageView imageView;
    RelativeLayout relativeLayout,drag=null;
    private DatabaseReference mDatabase;
    private PrefStorage prefStorage = null;
    private ArrayList<Upload> listUpload = new ArrayList<>();
    String albumUrl = null;
    Context c=this;
    LayoutInflater layoutInflater;
    GetSongs pickedSong;
    String notifystring;
    String notifystringsearch;
    public  static SongsActivity songsActivity;
    CircleMenu circleMenu;
    float xDown=0,yDown=0;
    RelativeLayout fullre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ImageView imageViewcancelnav,power;
    TextView txtemail;
    BottomSheetDialog bottomSheetDialog;
    private RecyclerviewInterface recyclerviewInterface;




    CircleImageView circleImageViewfoot;
    TextView emailfirstsongs;

    LinearLayout h1,s1,p1, l1,pro1;
    ImageView pause,play;


    public void setRecyclerviewInterface(RecyclerviewInterface recyclerviewInterface) {
        this.recyclerviewInterface = recyclerviewInterface;
    }

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getApplication();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_songs);
        play=findViewById(R.id.playiconsongs);
        pause=findViewById(R.id.pauseiconsongs);

        h1=findViewById(R.id.songhome);
        l1=findViewById(R.id.songlib);
        p1=findViewById(R.id.songup);
        s1=findViewById(R.id.songse);

        imageView=findViewById(R.id.imagviewfloat);
        drag=findViewById(R.id.drag);
        pro1=findViewById(R.id.songlyrics);
        emailfirstsongs=findViewById(R.id.emailfirstsongs);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        power=findViewById(R.id.powersongs);
        txtemail=findViewById(R.id.txtemailsongs);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        fullre=findViewById(R.id.resongs);
        profilerelative=findViewById(R.id.profilefornavsongs);
        helprelative=findViewById(R.id.Helpfornavsongs);
        karaokerelative=findViewById(R.id.karaokefornavsongs);
        chortchartrelative=findViewById(R.id.chordchartfornavsongs);
        imageViewcancelnav=findViewById(R.id.closenavsongs);
        notifystring= getIntent().getStringExtra("back");
        notifystringsearch=getIntent().getStringExtra("backs");
        s= getIntent().getStringExtra("songCategory");
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstsongs.setText(getgmail.toUpperCase());
        txtemail.setText(firebaseUser.getEmail().toString());
        fullre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        if(application.isEnblecolor()){
            fullre.setBackgroundColor(0xff121212);
        }
        if(myApplication.getSongCircle()){
            drag.setVisibility(View.VISIBLE);
            String ii=myApplication.getSongUrl();
            Picasso.get().load(ii).into(imageView);
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



        imageViewcancelnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roate);
                imageViewcancelnav.startAnimation(animation3);
                fullre.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                fullre.startAnimation(animation2);
                profilerelative.startAnimation(animation2);
                helprelative.startAnimation(animation2);
                karaokerelative.startAnimation(animation2);
                chortchartrelative.startAnimation(animation2);
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
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c, profile.class);
                startActivity(intent);
            }
        });

        relativeLayout=findViewById(R.id.layout);
        g=findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycleview);
        jcPlayerView = findViewById(R.id.jcplayer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mupload = new ArrayList<>();
        recyclerView.setAdapter(adapter);


        imageView.setOnTouchListener(new View.OnTouchListener() {

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

                        drag.setX(drag.getX()+distancex);
                        drag.setY(drag.getY()+distancey);

                        xDown=Movedx;
                        yDown=Movedy;

                        break;

                }
                return true;
            }
        });


        prefStorage = PrefStorage.getInstance();
        String category = getIntent().getStringExtra(KEY_SONG_CATEGORY);
        mDatabase= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String albumUrl = "";
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    listUpload.add(upload);
                    /*if (upload.getSongCategory().equals(category)) {
                        albumUrl = upload.getUrl();
                        break;
                    }*/
                }
                /*if (albumUrl != null && !albumUrl.isEmpty()) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.fod).error(R.mipmap.ic_launcher);
                    Glide.with(SongsActivity.this).load(albumUrl).apply(requestOptions).into(imageView);
                }*/
                //play();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        adapter=new JcSongsAdapter(c, mupload, new JcSongsAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int postion) {
                finish();
                Intent intent = new Intent(SongsActivity.this, playmusic.class);
                intent.putExtra(playmusic.KEY_SONG_TITLE, songs.getSongTitle());
                intent.putExtra(playmusic.KEY_SONG_LINK, songs.getSongLink());
                intent.putExtra(playmusic.KEY_SONG_CATEGORY, songs.getSongCategory());
                startActivity(intent);
                Animatoo.animateSlideUp(c);
                setPickedSong(songs);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mupload.clear();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    getSongs.setmKey(dss.getKey());
                    currentIndex = 0;
                    Log.v("SongsActivity"," value  "+s+", value 2" +getSongs.getSongCategory());
                    if (s != null && s.equals(getSongs.getSongCategory())||notifystring!=null&&notifystring.equals(getSongs.getSongCategory())||notifystringsearch!=null&&notifystringsearch.equals(getSongs.getSongCategory())) {
                        mupload.add(getSongs);
                        checkin = true;
                        Log.v("SongsActivity","Song Url "+getSongs.getSongLink());
                        jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                    }
                }

                adapter.setSeletedPosition(0);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                g.setVisibility(View.GONE);
                if (checkin) {
                    if(s!=null){
                        application.setNotify(s);
                    }
                    if(notifystring!=null){
                        application.setNotify(notifystring);
                    }

                    jcPlayerView.initPlaylist(jcAudios, null);
                }
                else {

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

                g.setVisibility(View.GONE);
            }
        });


    }


    public void  changeSelectedSong(int index){
        adapter.notifyItemChanged(adapter.getSeletedPosition());
        currentIndex=index;
        adapter.setSeletedPosition(currentIndex);
        adapter.notifyItemChanged(currentIndex);

    }

    public void btpop(View v){

    }

    private void setPickedSong(GetSongs song) {
        pickedSong = song;
    }


    public void showPopup(View v){
        fullre.setVisibility(View.VISIBLE);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        fullre.startAnimation(animation1);
        profilerelative.startAnimation(animation1);
        helprelative.startAnimation(animation1);
        karaokerelative.startAnimation(animation1);
        chortchartrelative.startAnimation(animation1);
    }

    @Override
    public void onBackPressed() {
        finish();
         Intent intent=new Intent(c, MainActivity.class);
        startActivity(intent);

    }
}
