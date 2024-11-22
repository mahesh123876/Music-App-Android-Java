package com.alban.gods;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class profile extends AppCompatActivity implements
        TextWatcher {
    ImageView voice,pausepro,playpro;
    CircleMenu circleMenu;
    FirebaseDatabase mdatabase;
    DatabaseReference mref;
    RecyclerView recyclerView;
    Context c=this;
    RelativeLayout relativeLayout;
    TextView txtvoice,textalpha;
    StudentAdapter studentAdapter;
    List<studentsModel> studentsMdlList;
    EditText editTextSearch = null;
    ArrayList<studentsModel> searchedList = new ArrayList();
    TextView a,b,c1,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z;
    CircleImageView circleImageView;
    float xDown=0,yDown=0;
    private MyApplication application=null;
    public  static  profile profile;
    ImageView libimageViewcancelnav;
    RelativeLayout libre,profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    BottomSheetDialog bottomSheetDialog;
    TextView textViewproemail;
    ImageView powerpro;
    TextView emailfirstpro;
    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1,pro2;
    GifImageView gifImageView;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        application = (MyApplication)getApplication();
        setContentView(R.layout.activity_profile);
        gifImageView=findViewById(R.id.profileprogress);
        powerpro=findViewById(R.id.propower);
        play=findViewById(R.id.playiconpro);
        pause=findViewById(R.id.pauseiconpro);
        textViewproemail=findViewById(R.id.protxtemail);

        profile=this;
        emailfirstpro=findViewById(R.id.emailfirstpro);
        libre=findViewById(R.id.prore);
        profilerelative=findViewById(R.id.proprofilefornav);
        helprelative=findViewById(R.id.proHelpfornav);
        karaokerelative=findViewById(R.id.prokaraokefornav);
        chortchartrelative=findViewById(R.id.prochordchartfornav);

        libimageViewcancelnav=findViewById(R.id.proclosenav);
        relativeLayout=findViewById(R.id.forloop);
        circleImageView=findViewById(R.id.imagviewdraglyrics);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirstpro.setText(getgmail.toUpperCase());


        libre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        powerpro.setOnClickListener(new View.OnClickListener() {
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
        textViewproemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        });       circleImageView.setOnTouchListener(new View.OnTouchListener() {

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





        textalpha=findViewById(R.id.txtalpha);
        a=findViewById(R.id.a1);
        b=findViewById(R.id.a2);
        c1=findViewById(R.id.a3);
        d=findViewById(R.id.a4);
        e=findViewById(R.id.a5);
        f=findViewById(R.id.a6);
        g=findViewById(R.id.a7);
        h=findViewById(R.id.a8);
        i=findViewById(R.id.a9);
        j=findViewById(R.id.a10);
        k=findViewById(R.id.a11);
        l=findViewById(R.id.a12);
        m=findViewById(R.id.a13);
        n=findViewById(R.id.a14);
        o=findViewById(R.id.a15);
        p=findViewById(R.id.a16);
        q=findViewById(R.id.a17);
        r=findViewById(R.id.a18);
        s=findViewById(R.id.a19);
        t=findViewById(R.id.a20);
        u=findViewById(R.id.a21);
        v=findViewById(R.id.a22);
        w=findViewById(R.id.a23);
        x=findViewById(R.id.a24);
        y=findViewById(R.id.a25);
        z=findViewById(R.id.a26);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("A");
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("B");
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("C");
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("D");
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("E");
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("F");
            }

        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("G");
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("H");
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("I");
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("J");
            }
        });
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("K");
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("L");
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("M");
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("N");
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("O");
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("P");
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("Q");
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("R");
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("S");
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("T");
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("U");
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("V");
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("W");
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("X");
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("Y");
            }
        });
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textalpha.setText("Z");
            }
        });





        voice=findViewById(R.id.lyricsvoice);
        txtvoice=findViewById(R.id.txtvoicef);
        h1=findViewById(R.id.promain);
        l1=findViewById(R.id.prolib);
        p1=findViewById(R.id.proup);
        s1=findViewById(R.id.prose);
        pro1=findViewById(R.id.prolyrics);
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
                Intent intent=new Intent(c,MainActivity.class);
                startActivity(intent);
            }
        });

        editTextSearch = findViewById(R.id.lyricssreach);
        editTextSearch.addTextChangedListener(this);
        mdatabase=FirebaseDatabase.getInstance();
        mref=mdatabase.getReference().child("students");
        recyclerView=findViewById(R.id.recyclelyrics);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentsMdlList=new ArrayList<studentsModel>();
        studentAdapter=new StudentAdapter(profile.this,studentsMdlList);
        recyclerView.setAdapter(studentAdapter);
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                studentsModel studentsModel=snapshot.getValue(studentsModel.class);
                studentsMdlList.add(studentsModel);
                Collections.sort(studentsMdlList, new Comparator<studentsModel>() {
                    @Override
                    public int compare(studentsModel o1, studentsModel o2) {
                        return o1.getFirstname().compareTo(o2.getFirstname());
                    }
                });
                studentAdapter.notifyDataSetChanged();

                gifImageView.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gifImageView.setVisibility(View.GONE);
            }
        });




        txtvoice.addTextChangedListener(new TextWatcher() {
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
                    studentAdapter.changeList((ArrayList<studentsModel>) studentsMdlList);
                } else {
                    searchText =  searchText.toLowerCase();
                    searchedList.clear();
                    for (studentsModel sm: studentsMdlList) {

                        if (sm.getFirstname().toLowerCase().startsWith(searchText)||sm.getLastname().toLowerCase().startsWith(searchText)) {
                            searchedList.add(sm);
                        }
                    }
                    studentAdapter.changeList(searchedList);
                }
            }
        });


        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeakVoice(view);
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

        textalpha.addTextChangedListener(new TextWatcher() {
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
                    studentAdapter.changeList((ArrayList<studentsModel>) studentsMdlList);
                } else {
                    searchText =  searchText.toLowerCase();
                    searchedList.clear();
                    for (studentsModel sm: studentsMdlList) {
                        if (sm.getFirstname().toLowerCase().startsWith(searchText)||sm.getLastname().toLowerCase().startsWith(searchText)) {
                            searchedList.add(sm);
                        }
                    }
                    studentAdapter.changeList(searchedList);
                }
            }
        });

if(application.isEnabledarkmode()){
    libre.setBackgroundColor(0xff121212);
}


        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);

    }


    private void SpeakVoice(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking");
        startActivityForResult(intent,112);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==112&&resultCode==RESULT_OK){
            txtvoice.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }

    private void searchSong(String searchValue) {
        searchedList.clear();
        String lwrSearchValue = searchValue.toLowerCase();
        for (studentsModel song: studentsMdlList) {
            if (song.getFirstname().toLowerCase().startsWith(lwrSearchValue)||song.getLastname().toLowerCase().startsWith(lwrSearchValue)) {
                searchedList.add(song);
            }
        }
        // Setup Adapter
        //adapter.setSeletedPosition(0);
        studentAdapter.notifyDataSetChanged();
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String searchText = editable.toString().trim();
        if (searchText.isEmpty()) {
            studentAdapter.changeList((ArrayList<studentsModel>) studentsMdlList);
        } else {
            searchText =  searchText.toLowerCase();
            searchedList.clear();
            for (studentsModel sm: studentsMdlList) {
                if (sm.getFirstname().toLowerCase().startsWith(searchText)||sm.getLastname().toLowerCase().startsWith(searchText)) {
                    searchedList.add(sm);
                }
            }
            studentAdapter.changeList(searchedList);
        }
    }
    public  void pro(){
        finish();
    }

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

}