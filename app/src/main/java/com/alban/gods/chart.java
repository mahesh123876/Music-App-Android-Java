package com.alban.gods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class chart extends AppCompatActivity {

    TextView tet;
    MyApplication application;
    Context c=this;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(c,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart);
        tet=findViewById(R.id.soon);



    }
}