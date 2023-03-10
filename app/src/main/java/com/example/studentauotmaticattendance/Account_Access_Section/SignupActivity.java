package com.example.studentauotmaticattendance.Account_Access_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentauotmaticattendance.MainActivity;
import com.example.studentauotmaticattendance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {


    private Button msignup;
    private TextView mlogin;
    private EditText mEmail,mPassword,mName;
    private ConstraintLayout layout;
    private ProgressDialog dialog;
    private Boolean wificonnected=false,mobileconnectd=false;
    String emailPattern = "[a-zA-Z0-9._-]+@akgec.ac.in";
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Student Register");
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    Intent intent=new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                    finish();
                    return;
                }
            }
        };
        mauth= FirebaseAuth.getInstance();
        mlogin=findViewById(R.id.login);
        msignup=findViewById(R.id.signup);
        mName= findViewById(R.id.name);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);


        layout=findViewById(R.id.layout);
        msignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
                        final String name = mName.getText().toString();
                        final String email = mEmail.getText().toString();
                        final String password = mPassword.getText().toString();

                        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                        if(networkInfo!=null && networkInfo.isConnected())
                        {
                            Log.d("tag","sdfgfdsdfdsdfd");
                            wificonnected=networkInfo.getType()== ConnectivityManager.TYPE_WIFI;
                            mobileconnectd=networkInfo.getType()== ConnectivityManager.TYPE_MOBILE;

                            if(wificonnected || mobileconnectd) {
                                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                                    if (email.matches(emailPattern))
                                    {
                                        dialog = new ProgressDialog(SignupActivity.this);
                                    dialog.setMessage("Signing up....");
                                    dialog.setCancelable(true);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                dialog.dismiss();
                                                mauth.fetchSignInMethodsForEmail(email).
                                                        addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                                                boolean check = !task.getResult().getSignInMethods().isEmpty();
                                                                if (!check) {
                                                                    Toast.makeText(SignupActivity.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(SignupActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                String userId = mauth.getCurrentUser().getUid();
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userId);
                                                HashMap userinfo = new HashMap();
                                                userinfo.put("name", name);
                                                userinfo.put("email", email);
                                                userinfo.put("branch", "default");
                                                userinfo.put("year", "default");
                                                userinfo.put("rollno", "default");
                                                userinfo.put("phone", "default");
                                                userinfo.put("gender", "default");
                                                userinfo.put("profilepic", "default");
                                                databaseReference.updateChildren(userinfo);
                                                DatabaseReference studentsub1reference = databaseReference.child("subjects").child("Maths");
                                                HashMap sub1info = new HashMap();
                                                sub1info.put("Attended", 0);
                                                sub1info.put("Total Classes", 0);
                                                sub1info.put("Total Ac", 0);
                                                studentsub1reference.updateChildren(sub1info);
                                                DatabaseReference studentsub2reference = databaseReference.child("subjects").child("Physics");
                                                HashMap sub2info = new HashMap();
                                                sub2info.put("Attended", 0);
                                                sub2info.put("Total Classes", 0);
                                                sub2info.put("Total Ac", 0);
                                                studentsub2reference.updateChildren(sub2info);
                                                DatabaseReference studentsub3reference = databaseReference.child("subjects").child("English");
                                                HashMap sub3info = new HashMap();
                                                sub3info.put("Attended", 0);
                                                sub3info.put("Total Classes", 0);
                                                sub3info.put("Total Ac", 0);
                                                studentsub3reference.updateChildren(sub3info);
                                                DatabaseReference studentsub4reference = databaseReference.child("subjects").child("Computer");
                                                HashMap sub4info = new HashMap();
                                                sub4info.put("Attended", 0);
                                                sub4info.put("Total Classes", 0);
                                                sub4info.put("Total Ac", 0);
                                                studentsub4reference.updateChildren(sub4info);
                                            }
                                        }
                                    });
                                }else{
                                        Toast.makeText(SignupActivity.this, "email should be from akgec domain", Toast.LENGTH_SHORT).show();
                                    }
                            }
                            }
                        }else{
                            Toast.makeText(SignupActivity.this,"No Internet", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                finish();
                return;
            }
        });
            }

            @Override
            protected void onStart() {
                super.onStart();
                mauth.addAuthStateListener(firebaseAuthStateListener);
            }

            @Override
            protected void onStop() {
                super.onStop();
                mauth.removeAuthStateListener(firebaseAuthStateListener);
            }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }
    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}