package com.alban.gods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class number extends AppCompatActivity {
  private CircleImageView circleImageView;
 private static  final  int PICK_IMAGE=1;
 private FirebaseAuth mauth;
 private FirebaseUser mcurrent;

 RatingBar ratingBar;
 Uri uri;
 TextView textView;
 MyApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        application = (MyApplication)getApplication();
        circleImageView=findViewById(R.id.profile);
        textView=findViewById(R.id.txtemail);
        ratingBar=findViewById(R.id.rate);
        mauth=FirebaseAuth.getInstance();
        mcurrent=mauth.getCurrentUser();
        textView.setText(mcurrent.getEmail().toUpperCase());
        Toast.makeText(this, "kasi    "+mcurrent.getEmail().toUpperCase(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "kasi    "+mcurrent.getDisplayName(), Toast.LENGTH_SHORT).show();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"sellor picture"),PICK_IMAGE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
          uri=data.getData();
          String url=uri.toString();
          application.setStoreprofileurl(url);
            Toast.makeText(this, "Uri"+url, Toast.LENGTH_SHORT).show();


        }
    }

}