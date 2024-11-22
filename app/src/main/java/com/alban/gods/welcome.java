package com.alban.gods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class welcome extends AppCompatActivity {


    Button btmn;
    Context c = this ;
    RelativeLayout signuprelativeLayout,loginlayout,guestlogin;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String login="guest@gmail.com";
    String pass="tech@123";
    @Override
    public void onBackPressed() {

        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        loginlayout=findViewById(R.id.loginlayout);
        signuprelativeLayout=findViewById(R.id.signuplayout);

        guestlogin=findViewById(R.id.loginasguest);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Animation animation1= AnimationUtils.loadAnimation(c,R.anim.slideinright);
        signuprelativeLayout.setAnimation(animation1);
        loginlayout.setAnimation(animation1);
        guestlogin.startAnimation(animation1);
        guestlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlelogin();
            }
        });
        loginlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,Register.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });
        signuprelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,LLogin.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });
    }
    private  void handlelogin(){

        firebaseAuth.signInWithEmailAndPassword(login,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(c, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(c,MainActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(c, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}