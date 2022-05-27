package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class DiaryAddVaccinePage extends AppCompatActivity {

    Button btnSave;
    EditText etPetName, etTime, etDate, etNote;
    RadioGroup radioGroup;
    RadioButton radioButton, rd_home, rd_pro;
    Spinner spn_vaccine;
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
        setContentView(R.layout.activity_add_vacination);

        btnSave = findViewById(R.id.btn_save);
        etPetName = findViewById(R.id.etvpetName);
        etTime = findViewById(R.id.etvtime);
        etDate = findViewById(R.id.etvdate);
        spn_vaccine = findViewById(R.id.spn_vaccinetype);
        radioGroup = findViewById(R.id.radioGroup);
        etNote = findViewById(R.id.et_notes);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DiaryAddVaccinePage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(DiaryAddVaccinePage.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void checkButton(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Cared: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    private void PerforAuth() {
        String uid = fAuth.getUid();
        String vpetName = etPetName.getText().toString();
        String vtime = etTime.getText().toString();
        String vdate = etDate.getText().toString();
        String vnotes = etNote.getText().toString();
        String vaccineIntake = spn_vaccine.getSelectedItem().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String cared = radioButton.getText().toString();

        if(vpetName.isEmpty()){
            etPetName.setError("Pet Name is required!");
            etPetName.requestFocus();
        }

        else if(vtime.isEmpty()){
            etTime.setError("Time is required!");
            etTime.requestFocus();
        }

        else if(vdate.isEmpty()){
            etDate.setError("Date is required!");
            etDate.requestFocus();
        }
        else if(vnotes.isEmpty()){
            etNote.setError("Notes is required!");
            etNote.requestFocus();
        }

        else{
            saveVaccineData();
        }
    }

    private void saveVaccineData() {
        progressBar.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference("Vaccine");

        String vaccineId = reference.push().getKey();
        String uid = fAuth.getUid();
        String vpetName = etPetName.getText().toString();
        String vtime = etTime.getText().toString();
        String vdate = etDate.getText().toString();
        String vnotes = etNote.getText().toString();
        String vaccineIntake = spn_vaccine.getSelectedItem().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String cared = radioButton.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("vaccineid", vaccineId);
        hashMap.put("petname", vpetName);
        hashMap.put("time", vtime);
        hashMap.put("date", vdate);
        hashMap.put("vaccineIntake", vaccineIntake);
        hashMap.put("cared", cared);
        hashMap.put("notes", vnotes);

        reference.child(uid).child(vaccineId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    etPetName.getText().clear();
                    etTime.getText().clear();
                    etDate.getText().clear();
                    etNote.getText().clear();

                    Toast.makeText(DiaryAddVaccinePage.this, "Vaccination Saved!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(DiaryAddVaccinePage.this, "Failed Saved", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}