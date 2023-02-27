package com.example.studentauotmaticattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studentauotmaticattendance.Account_Access_Section.GenderActivity;
import com.example.studentauotmaticattendance.Message_Section.MessageActivity;
import com.example.studentauotmaticattendance.Teacher_Section.View_Teacher.ViewTeacherActivity;
import com.example.studentauotmaticattendance.Attendance_section.View_Attendance.ViewAttendanceActivity;
import com.example.studentauotmaticattendance.Profile_section.MyProfileActivity;
import com.example.studentauotmaticattendance.Setting_section.SaveTheme;
import com.example.studentauotmaticattendance.Setting_section.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
  TextView mgentext;
  private LinearLayout mprofile,mattendance,mdashboard,macattendance,mtimetable;
  private  TextView personname;
//    private TextView username;
    private Menu mymenu;
    private MenuInflater menuitem;
    private String userid,gender,name,year,branch;
    private Animation pulse;

    ProgressBar progressBar;
    MenuInflater inflater;
    SaveTheme saveTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveTheme=new SaveTheme(this);
        if(saveTheme.getTheme()==true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
//        setTheme(someSettings.get(PREFFERED_THEME) ? R.style.AppTheme : R.style.CreamTheme);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
             setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       setTitle("Student Dashboard");


        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();
        personname=findViewById(R.id.personname);
       mprofile= (LinearLayout) findViewById(R.id.profile);
      mattendance=(LinearLayout) findViewById(R.id.attendance);
      macattendance=(LinearLayout) findViewById(R.id.acattendance);
      mdashboard=(LinearLayout) findViewById(R.id.dashboard);
      progressBar=findViewById(R.id.progressBar);
      mtimetable=findViewById(R.id.timetable);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userid);
      progressBar.setVisibility(View.VISIBLE);
      mdashboard.setVisibility(View.INVISIBLE);
        pulse = AnimationUtils.loadAnimation(this, R.anim.blink);
        progressBar.startAnimation(pulse);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            name = bundle.getString("name");
            if (!name.equals("")) {
                personname.setText("Hi,  " + name);
            }
        }
        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MyProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

        mattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewAttendanceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });
        macattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewTeacherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

        mtimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, TimeTableActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("year",year);
                bundle.putString("branch",branch);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mymenu=menu;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, mymenu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.setting)
        {
            Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            finish();
        }
        if(item.getItemId()==R.id.message)
        {
            Intent intent=new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            finish();
        }
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map map = (Map) snapshot.getValue();
                    name = map.get("name").toString();
                    year = map.get("year").toString();
                    String gen = map.get("gender").toString();
                   branch = map.get("branch").toString();
                    if (gen.equals("default")) {
                        Intent intent = new Intent(getApplicationContext(), GenderActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
//                        mymenu.findItem(R.id.setting).setChecked(false);
                        personname.setText("Hi,  " + name);
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.clearAnimation();
                        mdashboard.setVisibility(View.VISIBLE);

                        databaseReference.child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    Map map = (Map) snapshot.getValue();
                                    String receivedcount=map.get("Received Count").toString();
                                    int countreceived=Integer.valueOf(receivedcount);
                                    int newcount=(int)snapshot.child("Received Messages").getChildrenCount();
                                    if(countreceived!=newcount)
                                    {
                                        mymenu.getItem(1).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.newmessage));
                                    }else{
                                        mymenu.getItem(1).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.nomessage));

                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



}


}