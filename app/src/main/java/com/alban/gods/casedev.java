package com.alban.gods;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class casedev extends AppCompatActivity {


    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casedev);
        databaseReference = FirebaseDatabase.getInstance().getReference("userdetailsforavatar");
        databaseReference.child("-NTjM5PBs6zOtJ0hlEk9").child("profilePicturechat").setValue("godfidence");


    }
}