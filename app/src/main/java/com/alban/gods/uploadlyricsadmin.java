package com.alban.gods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadlyricsadmin extends AppCompatActivity {
FirebaseDatabase mdatabase;
DatabaseReference mref;
FirebaseStorage mstorage;
ImageButton imageButton;
EditText editfirst,editlast;
Button btninsert;
ProgressDialog progressDialog;
private static  final int Gallery_code=1;
Uri imageuri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadlyricsadmin);
        imageButton=findViewById(R.id.uploadimage);
        editfirst=findViewById(R.id.ed1);
        editlast=findViewById(R.id.ed2);
        btninsert=findViewById(R.id.insert);
        mdatabase=FirebaseDatabase.getInstance();
        mref=mdatabase.getReference().child("students");
        mstorage=FirebaseStorage.getInstance();
        progressDialog=new ProgressDialog(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_code);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
if(requestCode==Gallery_code && resultCode==RESULT_OK){
    imageuri=data.getData();
    imageButton.setImageURI(imageuri);
}
   btninsert.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String fn=editfirst.getText().toString().trim();
           String ln=editlast.getText().toString().trim();
           if(!(fn.isEmpty()&&ln.isEmpty()&&imageuri!=null)){
               progressDialog.setTitle("uploading");
               progressDialog.show();
               StorageReference filepath=mstorage.getReference().child("imagePost").child(imageuri.getLastPathSegment());
               filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                      @Override
                      public void onComplete(@NonNull Task<Uri> task) {
                          String t=task.getResult().toString();
                          DatabaseReference newpost=mref.push();
                          newpost.child("firstname").setValue(fn);
                          newpost.child("lastname").setValue(ln);
                          newpost.child("image").setValue(task.getResult().toString());
                          progressDialog.dismiss();
                          Intent intent=new Intent(uploadlyricsadmin.this,profile.class);
                          startActivity(intent);

                      }
                  });
                   }
               });

           }

       }
   });

    }
}