package com.alban.gods;

import android.app.ProgressDialog;
import android.content.Context;
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

public class imageslideradmin extends AppCompatActivity {
    FirebaseDatabase mdatabassetwo;
    DatabaseReference mreftwo;
    FirebaseStorage mstoragetwo;
    Button btn;
    ImageButton imageButton;
    private  static  final  int Gallery_codetwo=1;
    EditText txtimageslider;
    Uri imageUritwo=null;
    ProgressDialog progressDialogtwo;
    Button btnclear;
    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageslideradmin);
        btnclear=findViewById(R.id.deletealldata);
        imageButton=findViewById(R.id.imageplace);
        btn=findViewById(R.id.saveimageslider);
        txtimageslider=findViewById(R.id.textimageslider);
        mdatabassetwo=FirebaseDatabase.getInstance();
        progressDialogtwo=new ProgressDialog(this);
        mreftwo= mdatabassetwo.getReference().child("ImagesliderDATA");
        mstoragetwo=FirebaseStorage.getInstance();
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mreftwo.removeValue();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_codetwo);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_codetwo && resultCode == RESULT_OK) {
            imageUritwo = data.getData();
            imageButton.setImageURI(imageUritwo);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fun = txtimageslider.getText().toString().trim();
                if (!(fun.isEmpty() && imageUritwo != null)) {
                    progressDialogtwo.setTitle("uploading ");
                    progressDialogtwo.show();
                    StorageReference filepathdog = mstoragetwo.getReference().child("ImageSlider__Images").child(imageUritwo.getLastPathSegment());
                    filepathdog.putFile(imageUritwo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadurl2 = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    DatabaseReference newpoast = mreftwo.push();
                                    newpoast.child("fakename").setValue(fun);
                                    newpoast.child("fakeimage").setValue(task.getResult().toString());
                                    progressDialogtwo.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });

    }
}