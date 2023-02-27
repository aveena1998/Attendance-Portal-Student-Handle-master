package com.example.studentauotmaticattendance.Message_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.studentauotmaticattendance.MainActivity;
import com.example.studentauotmaticattendance.R;
import com.example.studentauotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private TextView memptylist;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mviewmsgadapter;
    private  RecyclerView.LayoutManager mviewmsglayoutManager;
    private ProgressBar mprogressbar;
    DatabaseReference userdb;
    int count=0;
    SaveTheme saveTheme;
    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveTheme=new SaveTheme(this);
        if(saveTheme.getTheme()==true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Remarks");
        mprogressbar=findViewById(R.id.progressBar);
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setNestedScrollingEnabled(false);
        mrecyclerView.setHasFixedSize(true);
        memptylist=findViewById(R.id.emptylist);
        memptylist.setVisibility(View.INVISIBLE);
        mviewmsglayoutManager=new LinearLayoutManager(MessageActivity.this);
        mrecyclerView.setLayoutManager(mviewmsglayoutManager);
        mviewmsgadapter=new View_MsgAdapter(getdatasetviewmsg(),MessageActivity.this);
        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userid);
        getRemarks();
        mrecyclerView.setAdapter(mviewmsgadapter);



    }

    private void getRemarks() {
        databaseReference.child("Messages").child("Received Messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot match : dataSnapshot.getChildren()) {
                        fetchmessageinfo(match.getKey());
                    }

                }
                else{
                    mprogressbar.setVisibility(View.INVISIBLE);
                    memptylist.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchmessageinfo(String key) {
        databaseReference.child("Messages").child("Received Messages").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name=snapshot.child("Created by").getValue().toString();
                    String message=snapshot.child("message").getValue().toString();
                   viewobject obk=new viewobject(name,message);
                   resultsViewMsg.add(obk);
                }
                mviewmsgadapter.notifyDataSetChanged();
                mprogressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    int newcount=(int)snapshot.child("Received Messages").getChildrenCount();
                    Map count=new HashMap();
                    count.put("Received Count",String.valueOf(newcount));
                    databaseReference.child("Messages").updateChildren(count);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<viewobject> resultsViewMsg=new ArrayList<viewobject>();
    private List<viewobject> getdatasetviewmsg() {
        return resultsViewMsg;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.leftstart,R.anim.rightend);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }
}