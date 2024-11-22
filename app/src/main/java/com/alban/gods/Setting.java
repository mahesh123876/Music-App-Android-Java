package com.alban.gods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alban.gods.Adapter.ProfileuseAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class Setting extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    Context c=this;
    int brightness;

    TextView textViewtheme;

    BottomSheetDialog bottomSheetDialog;


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ProfileuseAdapter profileuseAdapter;
    ArrayList<Userchat> userchatArrayList;
    GifImageView gifImageView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch(Exception e) {

        }
    }
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
    String currentgmail;
   public static Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        setting=this;
        gifImageView=findViewById(R.id.profileprogress);
        textViewtheme=findViewById(R.id.textviewtheme);

        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);
        currentgmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        databaseReference=FirebaseDatabase.getInstance().getReference("userdetailsforavatar");
        recyclerView=findViewById(R.id.settingsrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userchatArrayList=new ArrayList<>();
        profileuseAdapter=new ProfileuseAdapter(c,userchatArrayList);
        recyclerView.setAdapter(profileuseAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userchatArrayList.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Userchat genereUpload=dataSnapshot.getValue(Userchat.class);

                   if(currentgmail!=null&&currentgmail.equals(genereUpload.getEmailchat())){
                       userchatArrayList.add(0,genereUpload);
                   }

                }
                gifImageView.setVisibility(View.GONE);
                profileuseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gifImageView.setVisibility(View.GONE);
            }
        });

        // Get the audio manager



    }
}