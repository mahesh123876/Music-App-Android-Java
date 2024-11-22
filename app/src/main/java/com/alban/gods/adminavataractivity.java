package com.alban.gods;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alban.gods.Adapter.AdminAvatarAdapter;
import com.alban.gods.Adapter.Avatardata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class adminavataractivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    RecyclerView recyclerView;
  AdminAvatarAdapter adminAvatarAdapter;
    List<Avatardata> avatardata;
    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminavataractivity);
        recyclerView=findViewById(R.id.recycleviewadminavatar);
        databaseReference= FirebaseDatabase.getInstance().getReference("adminavatarstorage");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        avatardata=new ArrayList<>();
        adminAvatarAdapter=new AdminAvatarAdapter(c,avatardata);
        recyclerView.setAdapter(adminAvatarAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                avatardata.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Avatardata genereUpload=dataSnapshot.getValue(Avatardata.class);
                    avatardata.add(0,genereUpload);
                    adminAvatarAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}