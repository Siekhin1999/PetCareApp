package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiaryDetailActivity extends AppCompatActivity {

    String diaryId="";
    String petname, date, time, food, water, outdoor, health;
    EditText et_update_petname, et_update_date, et_update_time, et_update_foodintake, et_update_waterintake, et_update_outdoor, et_update_health;
    Button btn_update;
    private FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase database;
    private Context context;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        et_update_petname = findViewById(R.id.et_update_petName);
        et_update_date = findViewById(R.id.et_update_date);
        et_update_time = findViewById(R.id.et_update_time);
        et_update_foodintake = findViewById(R.id.et_update_foodIntake);
        et_update_waterintake = findViewById(R.id.et_update_waterIntake);
        et_update_outdoor = findViewById(R.id.et_update_outdoor);
        et_update_health = findViewById(R.id.et_update_health);
        btn_update = findViewById(R.id.btn_update);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Diary").child(fAuth.getUid());

        Intent intent = getIntent();
        diaryId = intent.getStringExtra("diaryId");

        petname = intent.getStringExtra("petname");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        food = intent.getStringExtra("foodIntake");
        water = intent.getStringExtra("waterIntake");
        outdoor = intent.getStringExtra("outdoor");
        health = intent.getStringExtra("health");

        et_update_petname.setText(petname);
        et_update_date.setText(date);
        et_update_time.setText(time);
        et_update_foodintake.setText(food);
        et_update_waterintake.setText(water);
        et_update_outdoor.setText(outdoor);
        et_update_health.setText(health);


    }
}