package com.alban.gods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifImageView;

public class lyrics extends AppCompatActivity {

    public static String KEY_IMAGE_URI = "com.example.lcproclientapp.imageuri";

    private ImageView imgViewLyrics;
    private Context c = this;
    private Intent extra = null;
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
                    Toast.makeText(context, "recive", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

    public static void open(Context context, studentsModel sModel) {
        Intent intent = new Intent(context, lyrics.class);
        intent.putExtra(KEY_IMAGE_URI,sModel.image);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lyrics);
        gifImageView=findViewById(R.id.lyricsprogress);
        imgViewLyrics = findViewById(R.id.activity_lyrics_imgView);
        extra = getIntent();
        // Load Image
        String imageUri = extra.getStringExtra(KEY_IMAGE_URI);
        Picasso.get().load(imageUri).into(imgViewLyrics);

        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerReceiver.ACTION_UPDATE_CATEGORY_IMAGE);
        registerReceiver(receiver,filter);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c,profile.class);
        startActivity(intent);
    }
}