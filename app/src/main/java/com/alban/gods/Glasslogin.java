package com.alban.gods;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.github.furkankaplan.fkblurview.FKBlurView;

public class Glasslogin extends AppCompatActivity {


    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glasslogin);
        relativeLayout=findViewById(R.id.ss);
        FKBlurView blurView = findViewById(R.id.fkBlurView);
        blurView.setBlur(this,relativeLayout,40);
    }
}