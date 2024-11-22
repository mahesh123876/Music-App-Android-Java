package com.alban.gods;


import static com.alban.gods.PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class upload extends AppCompatActivity  {

    ImageView pro2,web,playlib,pauselib;
    Context c=this;
    BottomNavigationView bottomNavigationView;
    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1;
    private  MyApplication application=null;
    CircleImageView circleImageView;
    RelativeLayout relativeLayout;
    String ff;
    float xDown=0,yDown=0;
    public  static  upload tp;
    CircleMenu circleMenu;
    ImageView uploadcenter;
    ImageView libimageViewcancelnav;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    BottomSheetDialog bottomSheetDialog;
    TextView uptextemail;
    TextView emailfirstup;
    ImageView uppower;

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



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication)getApplication();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);
        uploadcenter=findViewById(R.id.upload);
        emailfirstup=findViewById(R.id.emailfirstup);
        uptextemail=findViewById(R.id.uptxtemail);
        uppower=findViewById(R.id.uppower);
        tp=this;

        libre=findViewById(R.id.upre);

        profilerelative=findViewById(R.id.upprofilefornav);
        helprelative=findViewById(R.id.upHelpfornav);
        karaokerelative=findViewById(R.id.upkaraokefornav);
        chortchartrelative=findViewById(R.id.upchordchartfornav);

        libimageViewcancelnav=findViewById(R.id.upclosenav);
        relativeLayout=findViewById(R.id.forloopupload);
        circleImageView=findViewById(R.id.imagviewdragupload);
        h1=findViewById(R.id.uphome);
        l1=findViewById(R.id.uplib);
        p1=findViewById(R.id.upup);
        s1=findViewById(R.id.upsearch);
        pro1=findViewById(R.id.uplyrics);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstup.setText(getgmail.toUpperCase());

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);

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

        libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        uppower.setOnClickListener(new View.OnClickListener() {
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
        uptextemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        if(ii==null){
            Picasso.get().load(R.drawable.nothing).into(circleImageView);
        }
        else {
            Picasso.get().load(ii).into(circleImageView);
        }



        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,library.class);
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
        web=findViewById(R.id.upload);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("http://godfidencemusicapp.infinityfreeapp.com/?i=1");
            }
        });


    }

    private void gotoUrl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
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

public  void pr(){
        pauselib.setVisibility(View.INVISIBLE);
}
}