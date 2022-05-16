package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Diary3rdPage extends AppCompatActivity {

    Button btnSave;
    EditText etPetName, etTime, etDate, etFoodIntake, etWaterIntake, etOutdoor, etHealth;
    String time;
    int year, month, day, hour, minute;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    DatabaseReference reference;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary3rd_page);

        btnSave = findViewById(R.id.btn_save);
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

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Diary3rdPage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+ year;
                etDate.setText(date);
            }
        };

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Diary3rdPage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        time = amPmConverter(hourOfDay);
                        etTime.setText(time);
                    }
                },hour, minute, false);
                timePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
                Intent intent = new Intent(Diary3rdPage.this,DiaryFragment.class);
                startActivity(intent);
            }
        });

    }

    private String amPmConverter(int hourOfDay) {
        //condition for am & pm
        if(hourOfDay>=0 && hourOfDay<12){
            if (hourOfDay == 0){
                time = 12 + " : " + minute + " AM";
            }
            else{
                time = hourOfDay + " : " + minute + " AM";
            }
        }
        else {
            if(hourOfDay == 12){
                time = hourOfDay + " : " + minute + " PM";
            }
            else{
                hourOfDay = hourOfDay -12;
                time = hourOfDay + " : " + minute + " PM";
            }
        }
        return time;
    }

    private void PerforAuth() {
        String uid = fAuth.getUid();
        String petName = etPetName.getText().toString();
        String time = etTime.getText().toString();
        String date = etDate.getText().toString();
        String foodIntake = etFoodIntake.getText().toString();
        String waterIntake = etWaterIntake.getText().toString();
        String outdoor = etOutdoor.getText().toString();
        String health = etHealth.getText().toString();

        if(petName.isEmpty()){
            etPetName.setError("Pet Name is required!");
            etPetName.requestFocus();
        }

        if(time.isEmpty()){
            etTime.setError("Time is required!");
            etTime.requestFocus();
        }

        else if(date.isEmpty()){
            etDate.setError("Date is required!");
            etDate.requestFocus();
        }
        else if(foodIntake.isEmpty()){
            etFoodIntake.setError("Food Intake is required!");
            etFoodIntake.requestFocus();
        }
        else if(waterIntake.isEmpty()){
            etWaterIntake.setError("Water Intake is required!");
            etWaterIntake.requestFocus();
        }
        else if(outdoor.isEmpty()){
            etOutdoor.setError("Outdoor note is required!");
            etOutdoor.requestFocus();
        }
        else if(health.isEmpty()){
            etHealth.setError("Health note is required!");
            etHealth.requestFocus();
        }
        else{
            saveDiaryData();
        }



    }

    private void saveDiaryData() {
        String uid = fAuth.getUid();
        String petName = etPetName.getText().toString();
        String time = etTime.getText().toString();
        String date = etDate.getText().toString();
        String foodIntake = etFoodIntake.getText().toString();
        String waterIntake = etWaterIntake.getText().toString();
        String outdoor = etOutdoor.getText().toString();
        String health = etHealth.getText().toString();

        //firebase save diary data function
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("petname", petName);
        hashMap.put("time", time);
        hashMap.put("date", date);
        hashMap.put("foodIntake", foodIntake);
        hashMap.put("waterIntake", waterIntake);
        hashMap.put("outdoor", outdoor);
        hashMap.put("health", health);

        progressBar.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference("Diary");
        String diaryId = reference.push().getKey();
        reference.child(uid).child(diaryId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Diary3rdPage.this, "Diary Saved!",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(Diary3rdPage.this, "Failed Saved", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}