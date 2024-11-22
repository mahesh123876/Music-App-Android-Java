package com.alban.gods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class userupdate extends AppCompatActivity {
    ImageView h1,h2,l1,l2,p1,p2,s1,s2,pro1,pro2;
    Context c=this;
    EditText name , email,num;
    Button btn;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference reference=db.getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_userupdate);


        name= findViewById(R.id.name);
        email=findViewById(R.id.email);
        num=findViewById(R.id.num);
        btn=findViewById(R.id.btnupload);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name .getText().toString();
                String numb = num.getText().toString();
                String adress = email .getText().toString();


                HashMap<String,String>usermap=new HashMap<>();
                usermap.put("Name",Name);
                usermap.put("adress",adress);
                usermap.put("number",numb);


                reference.push().setValue(usermap);


                if(Name.isEmpty()){
                    name.setError("enter the name");
                }
                if(numb.isEmpty()){
                    num.setError("enter the number");
                }
                if(adress.isEmpty()){
                    email.setError("enter the email");
                }

                else{
                    Intent intent=new Intent(c,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(c, "Data Saved", Toast.LENGTH_SHORT).show();
                }


            }
        });






    }
}