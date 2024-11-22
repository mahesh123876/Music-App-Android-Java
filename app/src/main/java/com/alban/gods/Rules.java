package com.alban.gods;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Rules extends AppCompatActivity {

    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rules);
        Dialog dialog;
        dialog=new Dialog(c);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.sheet));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations=R.style.MyDialogAnimation;
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog dialog;
                dialog=new Dialog(c);
                dialog.setContentView(R.layout.dialog2layout);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.sheet));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations=R.style.MyDialogAnimation;
                dialog.setCancelable(false);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(c,MainActivity.class);
                        startActivity(intent);
                    }
                },5000);
            }
        },5000);

    }
}