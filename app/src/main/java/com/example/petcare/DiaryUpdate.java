package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class DiaryUpdate extends AppCompatActivity {

    Button btnUpdate;
    EditText etPetName, etTime, etDate, etFoodIntake, etWaterIntake, etOutdoor, etHealth;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_update);

        btnUpdate = findViewById(R.id.btn_update);
        etPetName = findViewById(R.id.et_petName);
        etTime = findViewById(R.id.et_time);
        etDate = findViewById(R.id.et_date);
        etFoodIntake = findViewById(R.id.et_foodIntake);
        etWaterIntake = findViewById(R.id.et_waterIntake);
        etOutdoor = findViewById(R.id.et_outdoor);
        etHealth = findViewById(R.id.et_health);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();



    }

    private void GetDiaryDataFromFirebase() {


    }
}