package com.alban.gods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class logoutsplash extends AppCompatActivity {
Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoutsplash);
        c=this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(c,reg.class);
                startActivity(intent);
                finish();
            }
        },1200);
    }
}