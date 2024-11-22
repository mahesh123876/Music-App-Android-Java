package com.alban.gods;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.furkankaplan.fkblurview.FKBlurView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forgetpassword extends AppCompatActivity {

    EditText editText_password_register, editText_email_for_register, editText_username_for_register;
    RelativeLayout showpasword, hidepassword,relativeLayoutloginbtn;
    FKBlurView loginmainlayout;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String gmailforregister, usernameforregister, passwordforregister;
    Context c=this;
    Dialog dialog;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        showpasword = findViewById(R.id.showpasswordchange);
        hidepassword = findViewById(R.id.hidepasswordchange);
        relativeLayoutloginbtn=findViewById(R.id.relativechangebtn);
        loginmainlayout=findViewById(R.id.loginmainlayoutchange);
        databaseReference = FirebaseDatabase.getInstance().getReference("userdetailsforavatar");
        editText_password_register = findViewById(R.id.edittext_password_for_change);
        editText_email_for_register = findViewById(R.id.edittext_email_for_change);
        editText_username_for_register=findViewById(R.id.edittext_phone_for_change);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dialog=new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog);
        cardView=dialog.findViewById(R.id.okcard);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_register.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpasword.setVisibility(View.INVISIBLE);
                hidepassword.setVisibility(View.VISIBLE);
            }
        });
        hidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_register.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpasword.setVisibility(View.VISIBLE);
                hidepassword.setVisibility(View.INVISIBLE);
            }
        });
        relativeLayoutloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gmailforregister=editText_email_for_register.getText().toString();
                passwordforregister=editText_password_register.getText().toString();
                usernameforregister=editText_username_for_register.getText().toString();
                if(usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.isEmpty()){
                    editText_email_for_register.setError("invalid input");
                    editText_username_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                }
                else if(usernameforregister.isEmpty()&&gmailforregister.length()>=4&&passwordforregister.length()>=4){
                    editText_username_for_register.setError("invalid input");
                }
                else if(gmailforregister.isEmpty()&&usernameforregister.length()>=4&&passwordforregister.length()>=4){
                    editText_email_for_register.setError("invalid input");
                }
                else if(passwordforregister.isEmpty()&&usernameforregister.length()>=4&&gmailforregister.length()>=4){
                    editText_password_register.setError("invalid input");
                }
                else if(usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.length()>=4){
                    editText_email_for_register.setError("invalid input");
                    editText_username_for_register.setError("invalid input");
                }
                else if(gmailforregister.isEmpty()&&usernameforregister.length()>=4&&passwordforregister.isEmpty()){
                    editText_password_register.setError("invalid input");
                    editText_email_for_register.setError("invalid input");
                }
                else  if(gmailforregister.length()>=4&&usernameforregister.isEmpty()&&passwordforregister.isEmpty()){
                    editText_username_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                }
                else  {
                   beginRecovery(gmailforregister);
                }


            }
        });

    }
    private void beginRecovery(String email) {

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    databaseReference.child(usernameforregister).child("profilePicturechat").setValue(passwordforregister);
                    Toast.makeText(c,"Check the mail and set the new password",Toast.LENGTH_LONG).show();
                    dialog.show();
                }
                else {

                    Toast.makeText(c,"Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(c, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }



    }