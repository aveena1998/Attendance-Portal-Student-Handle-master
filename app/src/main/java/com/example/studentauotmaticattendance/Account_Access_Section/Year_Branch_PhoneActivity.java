package com.example.studentauotmaticattendance.Account_Access_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentauotmaticattendance.MainActivity;
import com.example.studentauotmaticattendance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Year_Branch_PhoneActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private Button mconfirm;
    private Spinner mbranch,myear;
    private EditText mphone,mrollno;
    DatabaseReference databaseReference,studentdatabaseReference;
    FirebaseAuth mauth;
    int count=0;
    String branch,phoneno,gender,userid,year,rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_branch__phone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();
        studentdatabaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child("Students");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userid);
        mrollno=findViewById(R.id.rollno);
        mbranch=findViewById(R.id.branch);
        myear=findViewById(R.id.year);
        mphone=findViewById(R.id.phone);
        mconfirm=findViewById(R.id.confirm);
        ArrayAdapter<CharSequence> branchadapter = ArrayAdapter.createFromResource(this,
                R.array.branch_arrays, R.layout.spinner_text_color);
        branchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mbranch.setAdapter(branchadapter);

        ArrayAdapter<CharSequence> yearadapter = ArrayAdapter.createFromResource(this,
                R.array.year_arrays, R.layout.spinner_text_color);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myear.setAdapter(yearadapter);
        Bundle bundle = getIntent().getExtras();
         gender= bundle.getString("gender");

        mconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rollno = mrollno.getText().toString();
                phoneno = mphone.getText().toString();
                branch = mbranch.getSelectedItem().toString();
                year = myear.getSelectedItem().toString();

                if (!phoneno.isEmpty() && !rollno.isEmpty()) {

                    progressDialog = new ProgressDialog(Year_Branch_PhoneActivity.this);
                    progressDialog.setTitle("Completing Your Profile");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    studentdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
//                                CreateUserProfile();
                            }else {
                                int size=(int) snapshot.getChildrenCount();
                                Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
                                while (dataSnapshots.hasNext())
                                {
                                    size=(int) snapshot.getChildrenCount();
                                    count++;
                                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                                    String roll = dataSnapshotChild.child("rollno").getValue().toString();
//                                    Toast.makeText(Depart_PhoneActivity.this, roll+"    "+rollno, Toast.LENGTH_SHORT).show();
                                    if (roll.equals(rollno)){
                                        Toast.makeText(Year_Branch_PhoneActivity.this,"Roll no already exists",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        break;
                                    }
                                    if(count>=size)
                                    {
                                        CreateUserProfile();
                                    }
                                }
                            }
                        }

                        private void CreateUserProfile() {
                            Map userinfo = new HashMap();
                            userinfo.put("phone", phoneno);
                            userinfo.put("rollno", rollno);
                            userinfo.put("branch", branch);
                            userinfo.put("year", year);
                            userinfo.put("gender", gender);
                            databaseReference.updateChildren(userinfo);
                            Intent intent = new Intent(Year_Branch_PhoneActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(Year_Branch_PhoneActivity.this, "Profile Completed!", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(R.anim.rightstart, R.anim.leftend);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });




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

}