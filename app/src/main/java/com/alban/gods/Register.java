package com.alban.gods;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.furkankaplan.fkblurview.FKBlurView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText editText_password_login, editText_email_for_login;
    RelativeLayout showpasword, hidepassword,relativeLayoutloginbtn;
    TextView forgetpasswordtxt, allreadyhaveanaccount, registernoow;
    Context c=this;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FKBlurView loginmainlayout;
    String loginemail;

    String loginpass;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        forgetpasswordtxt = findViewById(R.id.forgetpasswordtext);
        registernoow = findViewById(R.id.registernow);
        relativeLayoutloginbtn = findViewById(R.id.relativeloginbtn);
        loginmainlayout = findViewById(R.id.loginmainlayout);
        showpasword = findViewById(R.id.showpassword);
        hidepassword = findViewById(R.id.hidepassword);
        loginmainlayout=findViewById(R.id.loginmainlayout);
        editText_password_login = findViewById(R.id.edittext_password_for_login);
        editText_email_for_login = findViewById(R.id.edittext_email_for_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        forgetpasswordtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, Forgetpassword.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });
        showpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_login.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpasword.setVisibility(View.INVISIBLE);
                hidepassword.setVisibility(View.VISIBLE);
            }
        });
        hidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_login.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpasword.setVisibility(View.VISIBLE);
                hidepassword.setVisibility(View.INVISIBLE);
            }
        });
        relativeLayoutloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginemail=editText_email_for_login.getText().toString();
                 loginpass=editText_password_login.getText().toString();
                if(loginemail.isEmpty()&&loginpass.isEmpty()){
                    editText_email_for_login.setError("invalid input");
                    editText_password_login.setError("invalid input");
                }
                else  if(loginemail.isEmpty()&&loginpass.length()>=4){
                    editText_email_for_login.setError("invalid input");
                }
                else if(loginemail.length()>=5&&loginpass.isEmpty()){
                    editText_password_login.setError("invalid input");
                }
               else {
                   progressDialog.show();
                   progressDialog.setCanceledOnTouchOutside(false);
                   progressDialog.setContentView(R.layout.progressdialog);
                   progressDialog.getWindow().setBackgroundDrawableResource(
                           android.R.color.transparent
                   );
                    handlelogin();
                }


            }
        });
        registernoow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ine=new Intent(c,LLogin.class);
                startActivity(ine);
                Animatoo.animateFade(c);
            }
        });

    }
    private  void handlelogin(){

        firebaseAuth.signInWithEmailAndPassword(loginemail,loginpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(c, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(c,MainActivity.class);
                    startActivity(intent);

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(c, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}