package com.alban.gods;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class login extends AppCompatActivity {
    TextView t,forget;
    EditText inemail, inpassword;
    Button btnlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    FirebaseUser mUser;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        c = this;
        t = findViewById(R.id.textView13);

        inemail = findViewById(R.id.egemail);
        inpassword = findViewById(R.id.egpassword);
        btnlogin = findViewById(R.id.loginbutton);
        mauth = FirebaseAuth.getInstance();
        mUser = mauth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, reg.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss=inemail.getText().toString();
                String ff=inpassword.getText().toString();
                if(ss.equals("godfidencemusicapp")&&ff.equals("654321")){
                    Intent intent=new Intent(c,uploadlyricsadmin.class);
                    startActivity(intent);
                }
                else if(ss.equals("godfidencemusicapp")&&ff.equals("123456")){
                    Intent intent=new Intent(c,imageslideradmin.class);
                    startActivity(intent);
                }
                perforlogin(inemail.getText().toString(),inpassword.getText().toString());
            }
        });

        PrefStorage prefStorage = PrefStorage.getInstance();
        String email = prefStorage.getEamil();
        String password = prefStorage.getPassword();
        inemail.setText(email);
        inpassword.setText(password);
        if (!email.isEmpty() && !password.isEmpty()) {
            perforlogin(email,password);
        }
    }

    private void gotoUrl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void perforlogin(String email, String password) {
        if (!email.matches(emailPattern)) {
            inemail.setError("enter Conext email");
        } else if (password.isEmpty() || password.length() < 6) {
            inpassword.setError("enter the proper password");
        } else {
            progressDialog.setMessage("Please wait while Login");
            progressDialog.setTitle("Login....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUsertonextActivity();
                        Toast.makeText(c, "login successfully", Toast.LENGTH_SHORT).show();
                        PrefStorage prefStorage = PrefStorage.getInstance();
                        prefStorage.saveEmailAndPassword(email,password);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(c, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private  void sendUsertonextActivity(){
        Intent intent=new Intent(c,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}