package com.alban.gods;



import static com.alban.gods.PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.alban.gods.Adapter.GenereRecycleViewAdapter;
import com.alban.gods.Adapter.Genere_Upload;
import com.alban.gods.Adapter.ImageSliderAdapter;
import com.alban.gods.Adapter.ImageSliderModel;
import com.alban.gods.Adapter.RecycleViewAdapter;
import com.alban.gods.Adapter.TrendingSongsRecycleViewAdapter;
import com.alban.gods.Adapter.Trending_Get_Songs;
import com.alban.gods.Adapter.Trending_Upload;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.hitomi.cmlibrary.CircleMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Model.Upload;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    ImageView nav,playmain,pausemain,albumarrow;
    Context c= this;
    LinearLayout h1,h2,l1,l2,p1,p2,s1,s2,pro1,pro2;
    private  int currentindexTrend;
    private List<Upload>uploads;


    float x1,y1,x2,y2;
    CircleImageView circleImageView;


    FirebaseDatabase mdatabasef;
    DatabaseReference mref;
    FirebaseStorage mstorage;
    RecyclerView recyclerViewslide;
    ImageSliderAdapter imageSliderAdapter;
    ArrayList<ImageSliderModel> imageSliderModelArrayList;

    BottomSheetDialog bottomSheetDialog;
    public  static MainActivity mainActivity;
    ArrayList<Userchat> searchedList = new ArrayList();






    RecyclerView genererecycleview;
    DatabaseReference Gmdatabase;
    private List<Genere_Upload> genere_uploadList;
    GenereRecycleViewAdapter Gadapter;
    EditText editTexttest;

    GoogleSignInClient googleSignInClient;





    RecyclerView Trendingrecycleview;
    DatabaseReference Tmdatabase;
    private List<Trending_Get_Songs> trendingUploadList;
    TrendingJcSonsAdapter trendingSongsRecycleViewAdapter;
    ImageView imageViewlogo;

    private  MyApplication application=null;
    float xDown=0,yDown=0;
    RelativeLayout relativeLayout;
    CircleMenu circleMenu;
    ImageView imageViewlo;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayoutmain;
    SlidingDrawer simpleSlidingDrawer1;
    RelativeLayout re;
    ViewGroup.LayoutParams params;
    ImageView imageViewcancelnav;
    TextView txttest,txttest2,txttest3;
    FirebaseUser firebaseUser;
    TextView txtemail;


    RelativeLayout profilerelative,helprelative,karaokerelative,chortchartrelative,themerelative;
    ImageView power;
    FirebaseAuth firebaseAuth;
    RelativeLayout relativeLayoutgmail;





    TextView emailfirst;
    TextView trending,albums,genere,home,libray,upload,search,profile;

    RelativeLayout setting;
    int countalbum,counttrend,countgenere,countart,countlyrics;
    MyApplication applicationtry;

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

    ScrollView scrollView;
    ImageView imageViewmainab;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication)getApplication();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.playiconmain);
        pause=findViewById(R.id.pauseiconmain);
        trending=findViewById(R.id.trendtext);
        albums=findViewById(R.id.txtalbums);
        genere=findViewById(R.id.txtGenere);
        power=findViewById(R.id.power);
        imageViewmainab=findViewById(R.id.mainab);
        txtemail=findViewById(R.id.txtemail);
        relativeLayoutgmail=findViewById(R.id.gmailrelative);
        emailfirst=findViewById(R.id.emailfirst);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        mainActivity=this;

        re=findViewById(R.id.re);

        helprelative=findViewById(R.id.Helpfornav);
        karaokerelative=findViewById(R.id.karaokefornav);
        chortchartrelative=findViewById(R.id.chordchartfornav);
        imageViewcancelnav=findViewById(R.id.closenav);
        relativeLayoutmain=findViewById(R.id.mainidforrelative);
        albumarrow=findViewById(R.id.albumsarrow);
        relativeLayout=findViewById(R.id.forloopmain);
        circleImageView=findViewById(R.id.imagviewdragmain);
        nav=findViewById(R.id.navtope);
        h1=findViewById(R.id.hmainact);
        l1=findViewById(R.id.lmainact);
        p1=findViewById(R.id.umainact);
        s1=findViewById(R.id.smainact);
        pro1=findViewById(R.id.pmainact);
        scrollView=findViewById(R.id.scrollView);
        layoutInflater=getLayoutInflater();
        profilerelative=findViewById(R.id.profilefornav);
        String gmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String getgmail= String.valueOf(gmail.charAt(0));
        emailfirst.setText(getgmail.toUpperCase());


        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);
        txtemail.setText(firebaseUser.getEmail().toString());
        Toast toast=new Toast(getApplicationContext());
        ImageView imageView=new ImageView(getApplicationContext());
        if(application.isEnabledarkmode()){
            re.setBackgroundColor(0xff121212);
        }
        if(application.isNotificationenble()){
            finish();
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("songs");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 countalbum = (int) dataSnapshot.getChildrenCount();

                // Do something with the count, such as displaying it or using it in your code
                application.setCalbum(countalbum);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible error scenarios
            }
        });
        DatabaseReference usersReftrend = FirebaseDatabase.getInstance().getReference().child("Trending");

        usersReftrend.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 counttrend = (int) dataSnapshot.getChildrenCount();
                 application.setCtrend(counttrend);

                // Do something with the count, such as displaying it or using it in your code
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible error scenarios
            }
        });
        DatabaseReference usersGenere = FirebaseDatabase.getInstance().getReference().child("Genere");

        usersGenere.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 countgenere = (int) dataSnapshot.getChildrenCount();
                 application.setCgenere(countgenere);
                     // Do something with the count, such as displaying it or using it in your code
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible error scenarios
            }
        });
        DatabaseReference usersart = FirebaseDatabase.getInstance().getReference().child("Artist");

        usersart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 countart = (int) dataSnapshot.getChildrenCount();
                 application.setCart(countart);

                // Do something with the count, such as displaying it or using it in your code
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible error scenarios
            }
        });
        DatabaseReference userlyrics = FirebaseDatabase.getInstance().getReference().child("students");

        userlyrics.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 countlyrics = (int) dataSnapshot.getChildrenCount();
                 application.setLyrics(countlyrics);
                // Do something with the count, such as displaying it or using it in your code
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible error scenarios
            }
        });

        scrollView.setVerticalScrollBarEnabled(false);

        profilerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(c,Setting.class);
                startActivity(intent);
            }
        });
        googleSignInClient = GoogleSignIn.getClient(c, GoogleSignInOptions.DEFAULT_SIGN_IN);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.BottomSheetStyle);
                View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.logoutbottom,(RelativeLayout)findViewById(R.id.layoutforloutbottom));
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
                                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            firebaseAuth.signOut();
                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Logout Failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                Intent intent=new Intent(c,welcome.class);
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

        albumarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,fullalbums.class);
                startActivity(intent);
            }
        });
        if(application.getSongCircle()){
            relativeLayout.setVisibility(View.VISIBLE);

        }

        String ii= application.getSongUrl();
        Picasso.get().load(ii).into(circleImageView);
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
        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(c,profile.class);
                startActivity(intent);
            }
        });

        if(!isconnected()){
            Dialog dialog;
            dialog=new Dialog(c);
            dialog.setContentView(R.layout.nointernet);
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.sheet));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations=R.style.MyDialogAnimation;
            dialog.setCancelable(false);
            dialog.show();
            Button ok;
            ok=dialog.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }




        mdatabasef=FirebaseDatabase.getInstance();
        mref=mdatabasef.getReference().child("ImagesliderDATA");
        mstorage=FirebaseStorage.getInstance();
        imageSliderModelArrayList=new ArrayList<>();
        recyclerViewslide=findViewById(R.id.imagesliderrecycleview);
        recyclerViewslide.setHasFixedSize(true);
        imageSliderAdapter=new ImageSliderAdapter(c,imageSliderModelArrayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewslide.setLayoutManager(linearLayoutManager);
        recyclerViewslide.setAdapter(imageSliderAdapter);
        recyclerViewslide.setVisibility(View.VISIBLE);
        LinearSnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewslide);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()<(imageSliderAdapter.getItemCount()-1)){
                    linearLayoutManager.smoothScrollToPosition(recyclerViewslide,new RecyclerView.State(),linearLayoutManager.findLastCompletelyVisibleItemPosition()+1);

                }

                else if(linearLayoutManager.findLastCompletelyVisibleItemPosition()<(imageSliderAdapter.getItemCount()+2)){
                    linearLayoutManager.smoothScrollToPosition(recyclerViewslide,new RecyclerView.State(),linearLayoutManager.findLastCompletelyVisibleItemPosition()-2);
                }

            }
        },0,3000);
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ImageSliderModel imageSliderModel=snapshot.getValue(ImageSliderModel.class);
                imageSliderModelArrayList.add(imageSliderModel);
                imageSliderAdapter.notifyDataSetChanged();
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

            }
        });
        LinearLayoutManager linearLayoutManagerGC=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        genererecycleview=findViewById(R.id.genererecycleview);
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
        LinearLayoutManager linearLayoutManagerTR=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Trendingrecycleview=findViewById(R.id.trendingsongrecycleview);
        trendingUploadList=new ArrayList<>();
        Tmdatabase=FirebaseDatabase.getInstance().getReference("Trending");
        trendingSongsRecycleViewAdapter=new TrendingJcSonsAdapter(c, trendingUploadList, new TrendingJcSonsAdapter.TrendingRecyclerViewClickListener() {
            @Override
            public void onClickListener(Trending_Get_Songs songs, int postion) {

                changeSelectedSong(postion);
                //jcPlayerViewTrend.playAudio(jcAudiosTrend.get(postion));
                // jcPlayerViewTrend.setVisibility(View.VISIBLE);
                //jcPlayerViewTrend.createNotification();
                Intent intent = new Intent(c, playmusic.class);
                intent.putExtra("trendingsongtitle", songs.getSongTitle());
                intent.putExtra("trendingsonglink", songs.getSonglink());
                intent.putExtra("trendingsongcate", songs.getSongCategory());
                startActivity(intent);
                Animatoo.animateSlideUp(c);
            }
        });

        Tmdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trendingUploadList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Trending_Get_Songs trending_upload=dataSnapshot.getValue(Trending_Get_Songs.class);
                    trendingUploadList.add(0,trending_upload);

                }
                Trendingrecycleview.setVisibility(View.VISIBLE);
                Trendingrecycleview.setLayoutManager(linearLayoutManagerTR);
                Trendingrecycleview.setHasFixedSize(true);
                Trendingrecycleview.setAdapter(trendingSongsRecycleViewAdapter);
                trendingSongsRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        recyclerView=findViewById(R.id.recycleviewmain);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        uploads=new ArrayList<>();

        mDatabase= FirebaseDatabase.getInstance().getReference("uploads");
        adapter = new RecycleViewAdapter(MainActivity.this,uploads);
        recyclerView.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    uploads.add(0,upload);
                    Log.v("MainActivity","Upload value "+upload.getSongCategory()+", Url "+upload.getUrl());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
           }
        });



    }



    private boolean isconnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(c.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null&&connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
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

    @Override

    public void onBackPressed () {

        Dialog dialog;
        dialog=new Dialog(c);
        dialog.setContentView(R.layout.customexit);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.sheet));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations=R.style.MyDialogAnimation;
        dialog.setCancelable(true);
        dialog.show();
        CircleImageView wr,cr;
        wr=dialog.findViewById(R.id.wrong);
        cr=dialog.findViewById(R.id.correct);
        wr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                finishAndRemoveTask();

            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {

        }
    }
    public  void main(){
        pausemain.setVisibility(View.INVISIBLE);
    }

    public void  changeSelectedSong(int index){
        trendingSongsRecycleViewAdapter.notifyItemChanged(trendingSongsRecycleViewAdapter.getTSeletedPosition());
        currentindexTrend=index;
        trendingSongsRecycleViewAdapter.setTSeletedPosition(currentindexTrend);
        trendingSongsRecycleViewAdapter.notifyItemChanged(currentindexTrend);
    }
}