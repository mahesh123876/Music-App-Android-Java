package com.alban.gods;



import static com.alban.gods.MyApplication.application;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class newsplash extends AppCompatActivity {

    Context c=this;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsplash);
        // Get the system UI mode manager
        UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

// Check if night mode is already active

        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {

            // Night mode is already on

            application.setEnabledarkmode(true);
            application.setEnblecolor(true);

        }
        imageView=findViewById(R.id.shimmer);
        Animation animation= AnimationUtils.loadAnimation(c,R.anim.bounce);
        imageView.setVisibility(View.VISIBLE);
        imageView.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(c,fg1.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}