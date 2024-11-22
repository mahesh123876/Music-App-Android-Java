package com.alban.gods;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class reg extends AppCompatActivity {
    TextView alreadyhaveaccount;
    EditText inemail,inpassword,inconfirmpassword;
    Button btnregister;
    Context c;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mauth;

    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reg);
        c=this;
        alreadyhaveaccount=findViewById(R.id.alaccount);
        inemail=findViewById(R.id.email1);
        inpassword=findViewById(R.id.password1);
        inconfirmpassword=findViewById(R.id.password2);
        btnregister=findViewById(R.id.regbtn);
        mauth=FirebaseAuth.getInstance();
        mUser=mauth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c,login.class);
                startActivity(intent);
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }
    private  void PerforAuth(){
        String email=inemail.getText().toString();
        String password=inpassword.getText().toString();
        String confirmPassword=inconfirmpassword.getText().toString();

        if(!email.matches(emailPattern)){
            inemail.setError("enter Conext email");
        }
        else  if(password.isEmpty() || password.length()<6){
            inpassword.setError("enter the proper password");
        }
        else if(!password.equals(confirmPassword)){
            inconfirmpassword.setError("password not matched both field");
        }
        else{
            progressDialog.setMessage("Please wait while registeration");
            progressDialog.setTitle("Registeration....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUsertonextActivity();
                        Toast.makeText(c, "Registeration successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(c, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private  void sendUsertonextActivity(){
        Intent intent=new Intent(c,login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}