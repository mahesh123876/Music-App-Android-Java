package com.alban.gods;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alban.gods.Adapter.Constants;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.furkankaplan.fkblurview.FKBlurView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LLogin extends AppCompatActivity {


    TextView textView1, txt2, txt3, txt4;
    RelativeLayout showpasword, hidepassword, showpaswordforregister, hidepasswordforregister;
    Context c = this;
    ImageView closeregiter, closelogin;
    CardView chooseimage;
    RelativeLayout relativeLayoutloginbtn, relativeLayoutregisterbtn;
    BottomSheetDialog bottomSheetDialog;
    MediaPlayer mediaPlayer;
    NotificationChannel channel;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    NotificationManagerCompat managerCompat;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Uri imageuri = null;
    String imguristring;
    FirebaseStorage mstoragetwo;
    DatabaseReference databaseReference;
    int PICK_IMAGE_REQUESE = 1;
    StorageReference storageReference;
    String url;
    ImageView Setimage;
    EditText editText_password_login, editText_email_for_login, editText_password_register, editText_email_for_register, editText_username_for_register,editText_phone_for_register;


    String gmailforregister, usernameforregister, passwordforregister,phonenumberforregister;
    String gmailforlogin, passwordforlogin;
    TextView forgetpasswordtxt, allreadyhaveanaccount, registernoow;
    FKBlurView loginmainlayout, registermainlayout;
    RelativeLayout supportloginmainlayout, supportregistermainlayout;
   ProgressBar gifImageViewregistr;
   ProgressDialog progressDialog;
    String name;
    String email;
    String pass;
    String phonenumber;
    // Initialize variables
    CardView btSignIn;
    GoogleSignInClient googleSignInClient;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llogin);
        textView1 = findViewById(R.id.txtm1);
        txt2 = findViewById(R.id.txtm2);
        txt3 = findViewById(R.id.txtm3);
        progressDialog=new ProgressDialog(this);
        registermainlayout=findViewById(R.id.registermainlayout);
        gifImageViewregistr=findViewById(R.id.progressforregister);
        txt4 = findViewById(R.id.txtm4);
        supportregistermainlayout=findViewById(R.id.supportforregistermainlayout);
        allreadyhaveanaccount = findViewById(R.id.alredyhaveanaccount);
        Setimage = findViewById(R.id.setimage);
        chooseimage = findViewById(R.id.chooseimage);
        supportregistermainlayout = findViewById(R.id.supportforregistermainlayout);
        relativeLayoutregisterbtn = findViewById(R.id.relativeRegistertbtn);
        editText_password_register = findViewById(R.id.edittext_password_for_register);
        editText_email_for_register = findViewById(R.id.edittext_email_for_register);
        editText_username_for_register = findViewById(R.id.edittext_username_for_register);
        editText_phone_for_register=findViewById(R.id.edittext_phone_for_register);

        closeregiter = findViewById(R.id.closeregister);

        showpaswordforregister = findViewById(R.id.showpasswordforregister);
        hidepasswordforregister = findViewById(R.id.hidepasswordforregister);
        mediaPlayer = MediaPlayer.create(c, R.raw.notify);
        databaseReference = FirebaseDatabase.getInstance().getReference("userdetailsforavatar");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mstoragetwo = FirebaseStorage.getInstance();

        btSignIn = findViewById(R.id.bt_sign_in);

        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(c, googleSignInOptions);

        btSignIn.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });

        registermainlayout.setBlur(c,registermainlayout,5);
        url = getIntent().getStringExtra("imageurlforavatar");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.baseline_add_photo_alternate_24).error(R.drawable.baseline_add_photo_alternate_24);
        Glide.with(c).load(url).apply(requestOptions).into(Setimage);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(c, MainActivity.class);
            startActivity(intent);
        }
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideinright);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottomup);
        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottomdown);
        textView1.startAnimation(animation1);
        txt2.startAnimation(animation1);
        txt3.startAnimation(animation1);
        txt4.startAnimation(animation1);
        storageReference = FirebaseStorage.getInstance().getReference();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("MyNotify", "my channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(c, "MyNotify");
        builder.setContentText("Happy to Start your Feelings with PRISCILLIC Family");
        builder.setContentTitle(" Welcome !");
        builder.setSmallIcon(R.drawable.nothing);
        builder.setAutoCancel(true);
        builder.setColorized(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.nothing));
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setColor(getResources().getColor(R.color.teal_200));
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        managerCompat = NotificationManagerCompat.from(c);
        showpaswordforregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_register.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpaswordforregister.setVisibility(View.INVISIBLE);
                hidepasswordforregister.setVisibility(View.VISIBLE);
            }
        });
        hidepasswordforregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password_register.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpaswordforregister.setVisibility(View.VISIBLE);
                hidepasswordforregister.setVisibility(View.INVISIBLE);
            }
        });






        relativeLayoutregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameforregister = editText_username_for_register.getText().toString();
                gmailforregister = editText_email_for_register.getText().toString();
                passwordforregister = editText_password_register.getText().toString();
                phonenumberforregister=editText_phone_for_register.getText().toString();

                if(usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.isEmpty()&&phonenumberforregister.isEmpty()){
                    editText_email_for_register.setError("invalid input");
                    editText_username_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                } else if (usernameforregister.isEmpty()&&gmailforregister.length()>=5&&passwordforregister.length()>=5&&phonenumberforregister.length()==10) {
                    editText_username_for_register.setError("invalid input");

                } else if (usernameforregister.length()>=5&&gmailforregister.isEmpty()&&passwordforregister.length()>=5&&phonenumberforregister.length()>=10) {
                    editText_email_for_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.length()>=5&&passwordforregister.isEmpty()&&phonenumberforregister.length()>=10) {
                    editText_password_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.length()>=5&&passwordforregister.length()>=5&&phonenumberforregister.isEmpty()) {
                    editText_phone_for_register.setError("invalid input");
                } else if (usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.length()>=5&&phonenumberforregister.length()>=10) {
                    editText_email_for_register.setError("invalid input");
                    editText_username_for_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.length()>=5&&passwordforregister.isEmpty()&&phonenumberforregister.isEmpty()) {
                    editText_password_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                } else if (usernameforregister.isEmpty()&&gmailforregister.length()>=5&&passwordforregister.isEmpty()&&phonenumberforregister.length()>=10) {
                    editText_username_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                } else if (usernameforregister.isEmpty()&gmailforregister.length()>=5&&passwordforregister.length()>=5&&phonenumberforregister.isEmpty()) {
                    editText_username_for_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.isEmpty()&&passwordforregister.isEmpty()&&phonenumberforregister.length()>=10) {
                    editText_email_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.isEmpty()&&passwordforregister.length()>=5&&phonenumberforregister.isEmpty()) {
                    editText_email_for_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                } else if (usernameforregister.length()>=5&&gmailforregister.isEmpty()&&passwordforregister.isEmpty()&&phonenumberforregister.isEmpty()) {
                   editText_email_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                }
                else if (usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.isEmpty()&&phonenumberforregister.length()>=10) {
                    editText_username_for_register.setError("invalid input");
                    editText_email_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                }
               else if (usernameforregister.isEmpty()&&gmailforregister.isEmpty()&&passwordforregister.length()>=5&&phonenumberforregister.isEmpty()) {
                    editText_username_for_register.setError("invalid input");
                   editText_email_for_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                }
                else if (usernameforregister.isEmpty()&&gmailforregister.length()>=5&&passwordforregister.isEmpty()&&phonenumberforregister.isEmpty()) {
                    editText_username_for_register.setError("invalid input");
                    editText_password_register.setError("invalid input");
                    editText_phone_for_register.setError("invalid input");
                } else  {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progressdialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );
                    handlesignup();
                }
            }
        });

        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(c, R.style.BottomSheetStyle);
                View view = LayoutInflater.from(c).inflate(R.layout.bottomavatar, (RelativeLayout) findViewById(R.id.bottomrelativceavatar));
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                CardView cardView1, cardView2;
                cardView1 = bottomSheetDialog.findViewById(R.id.ownimageavatar);
                cardView2 = bottomSheetDialog.findViewById(R.id.adminappimageavatar);
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                         name=editText_username_for_register.getText().toString();
                         email=editText_email_for_register.getText().toString();
                         pass=editText_password_register.getText().toString();
                         phonenumber=editText_phone_for_register.getText().toString();
                         MyApplication.application.setGmail(email);
                         MyApplication.application.setUsername(name);
                         MyApplication.application.setPassword(pass);
                         MyApplication.application.setPhonenumber(phonenumber);
                        Intent intent = new Intent(c, adminavataractivity.class);
                        startActivity(intent);

                    }
                });
            }
        });
        editText_username_for_register.setText(MyApplication.application.getUsername());
        editText_email_for_register.setText(MyApplication.application.getGmail());
        editText_password_register.setText(MyApplication.application.getPassword());
        editText_phone_for_register.setText(MyApplication.application.getPhonenumber());

        allreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,Register.class);
                startActivity(intent);
                Animatoo.animateFade(c);
            }
        });


    }

    private void handlesignup() {



         if(imageuri==null&&url!=null){
            signupfunction();
            startnotification();
        }
        else if(imageuri!=null){
            final StorageReference sRef=storageReference.child(Constants.STORAGE_PATH_UPLOADS +System.currentTimeMillis()+"."+getFileExtension(imageuri));
            sRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url=uri.toString();
                            signupfunction();
                            startnotification();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(c, "failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });

        }
        else{
            progressDialog.dismiss();
            Toast.makeText(c, "Select the Image ", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK) {
            imageuri = data.getData();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.baseline_add_photo_alternate_24).error(R.drawable.baseline_add_photo_alternate_24);
            Glide.with(c).load(imageuri).apply(requestOptions).into(Setimage);
            bottomSheetDialog.dismiss();
        }


        else if (requestCode == 100) {
            Toast.makeText(c, "100", Toast.LENGTH_SHORT).show();
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
                                    startActivity(new Intent(c, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    displayToast("Google authentication successful");
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                    Toast.makeText(c, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
           System.out.println("nu");
        }
    }
    public  String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getMimeTypeFromExtension(cr.getType(uri));

    }
    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
        private  void signupfunction(){
            firebaseAuth.createUserWithEmailAndPassword(gmailforregister,passwordforregister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Userchat userchat=new Userchat(usernameforregister,gmailforregister,passwordforregister,url,phonenumberforregister);
                        String gg=phonenumberforregister;
                        databaseReference.child(gg).setValue(userchat);
                        progressDialog.dismiss();
                        Intent intent=new Intent(c,Rules.class);
                        startActivity(intent);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(c, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        private void startnotification(){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ;
            }
            managerCompat.notify(1, builder.build());
            mediaPlayer.start();
        }
}