package com.example.studentauotmaticattendance.Account_Access_Section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studentauotmaticattendance.R;

public class ChooseLoginRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registration);

        Button mlogin=findViewById(R.id.login);
        Button msignup=findViewById(R.id.signup);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(getApplication(), LoginActivity.class);
                 startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                 finish();
                 return;
            }
        });
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                finish();
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}