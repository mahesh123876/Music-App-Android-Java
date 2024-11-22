package com.alban.gods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class fg2 extends AppCompatActivity {

    TextView skip,next;
    Context c=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fg2);
        skip=findViewById(R.id.skip);
        next=findViewById(R.id.next);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,betaversion.class);
                startActivity(intent);
                Animatoo.animateFade(c);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,betaversion.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });


    }
}