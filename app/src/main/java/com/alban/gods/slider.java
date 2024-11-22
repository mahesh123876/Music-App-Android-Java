package com.alban.gods;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class slider extends AppCompatActivity {


ImageView btntest;
EditText editTextsearch;
TextView settext;
RecyclerView recyclerViewforget;
    DatabaseReference Tmdatabase;
    private List<Userchat> trendingUploadList;
    ForgetAdapter trendingSongsRecycleViewAdapter;
    Context c=this;
    ArrayList<Userchat> searchedList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        btntest=findViewById(R.id.find);
        editTextsearch=findViewById(R.id.searchgmail);
        settext=findViewById(R.id.settext);
        recyclerViewforget=findViewById(R.id.forgetrecycleview);
        recyclerViewforget.setVisibility(View.INVISIBLE);

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getgamil=editTextsearch.getText().toString();
                settext.setText(getgamil);
            }
        });
        settext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerViewforget.setVisibility(View.VISIBLE);
                String searchText = s.toString().trim();
                if (searchText.isEmpty()) {
                    trendingSongsRecycleViewAdapter.changeList((ArrayList<Userchat>) trendingUploadList);
                } else {
                    searchText =  searchText.toLowerCase();
                    searchedList.clear();
                    for (Userchat sm: trendingUploadList) {
                        if (sm.getEmailchat().toLowerCase().startsWith(searchText)) {
                            searchedList.add(sm);
                        }
                    }
                    trendingSongsRecycleViewAdapter.changeList(searchedList);
                }
            }
        });

        recyclerViewforget.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewforget.setHasFixedSize(true);
        trendingUploadList=new ArrayList<>();
        Tmdatabase= FirebaseDatabase.getInstance().getReference("userdetailsforavatar");
        trendingSongsRecycleViewAdapter=new ForgetAdapter(c,trendingUploadList);
        recyclerViewforget.setAdapter(trendingSongsRecycleViewAdapter);
        Tmdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trendingUploadList.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Userchat trending_upload=dataSnapshot.getValue(Userchat.class);
                    trendingUploadList.add(0,trending_upload);
                    trendingSongsRecycleViewAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}