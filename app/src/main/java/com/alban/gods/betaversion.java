package com.alban.gods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class betaversion extends AppCompatActivity {


    TextView skipbeta,nextbeta;
    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betaversion);
        skipbeta=findViewById(R.id.skipbeta);
        nextbeta=findViewById(R.id.nextbeta);
        skipbeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,welcome.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });
        nextbeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,welcome.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });
    }
}