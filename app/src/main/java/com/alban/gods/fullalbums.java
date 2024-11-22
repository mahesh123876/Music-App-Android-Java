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
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Adapter.RecycleViewAdapter;

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

import Model.Upload;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class fullalbums extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    DatabaseReference mDatabase;
    ImageView fullalbumvoice;
    Context c= this;
    private List<Upload> uploads;
    EditText editText;
    ArrayList<Upload> searchedList = new ArrayList();
    TextView textView;
    RelativeLayout fullre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ImageView imageViewcancelnav,power,sefull;
    TextView txtemail;
    BottomSheetDialog bottomSheetDialog;
    TextView emailfirst;
    RelativeLayout relativeLayout;
    float xDown=0,yDown=0;
    CircleImageView circleImageView;
    CircleMenu circleMenu;

    LinearLayout h1,s1,p1, l1,pro1;
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
        Intent intent=new Intent(c,MainActivity.class);
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
        setContentView(R.layout.activity_fullalbums);
        play=findViewById(R.id.playiconfull);
        pause=findViewById(R.id.pauseiconfull);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        power=findViewById(R.id.powerfull);
        txtemail=findViewById(R.id.txtemailfull);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        sefull=findViewById(R.id.serchmainforfull);
        txtemail.setText(firebaseUser.getEmail().toString());
        emailfirst=findViewById(R.id.emailfirstfull);
        h1=findViewById(R.id.fullhome);
        l1=findViewById(R.id.fulllib);
        p1=findViewById(R.id.fullup);
        s1=findViewById(R.id.fullse);
        pro1=findViewById(R.id.fulllyrics);

        fullre=findViewById(R.id.refull);
        profilerelative=findViewById(R.id.profilefornavfull);
        helprelative=findViewById(R.id.Helpfornavfull);
        karaokerelative=findViewById(R.id.karaokefornavfull);
        chortchartrelative=findViewById(R.id.chordchartfornavfull);

        imageViewcancelnav=findViewById(R.id.closenavfull);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());
        recyclerView=findViewById(R.id.fullalbums);
        editText=findViewById(R.id.activity_fullalbum_editText);
        fullalbumvoice=findViewById(R.id.fullalbumsvoice);
        textView=findViewById(R.id.voicetxt);
        relativeLayout=findViewById(R.id.forloopfull);
        circleImageView=findViewById(R.id.imagviewdraglyricsfull);


        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);

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


        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);
            String ii=application.getSongUrl();
            Picasso.get().load(ii).into(circleImageView);
        }

        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,Setting.class);
                startActivity(intent);
            }
        });
        if(application.isEnblecolor()){
            fullre.setBackgroundColor(0xff121212);
        }
        sefull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent inte=new Intent(c,searchview.class);
                startActivity(inte);
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
                fullre.setVisibility(View.INVISIBLE);
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout);
                fullre.startAnimation(animation2);
                profilerelative.startAnimation(animation2);
                helprelative.startAnimation(animation2);
                karaokerelative.startAnimation(animation2);
                chortchartrelative.startAnimation(animation2);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        uploads=new ArrayList<>();
        mDatabase= FirebaseDatabase.getInstance().getReference("uploads");
        adapter = new RecycleViewAdapter(fullalbums.this,uploads);
        recyclerView.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    uploads.add(0,upload);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        fullalbumvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeakVoice(view);
            }
        });
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                if (searchText.isEmpty()) {
                    adapter.changeList((ArrayList<Upload>) uploads);
                } else {
                    searchText =  searchText.toLowerCase();
                    searchedList.clear();
                    for (Upload sm: uploads) {
                        if (sm.getName().toLowerCase().startsWith(searchText)) {
                            searchedList.add(sm);
                        }
                    }
                    adapter.changeList(searchedList);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                if (searchText.isEmpty()) {
                    adapter.changeList((ArrayList<Upload>) uploads);
                } else {
                    searchText =  searchText.toLowerCase();
                    searchedList.clear();
                    for (Upload sm: uploads) {
                        if (sm.getName().toLowerCase().startsWith(searchText)) {
                            searchedList.add(sm);
                        }
                    }
                    adapter.changeList(searchedList);
                }
            }
        });


    }

    private void SpeakVoice(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking");
        startActivityForResult(intent,112);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==112&&resultCode==RESULT_OK){
            textView.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));

        }
    }
}