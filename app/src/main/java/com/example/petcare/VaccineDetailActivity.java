package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.petcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VaccineDetailActivity extends AppCompatActivity {

    String vaccineId="";
    String petname, date, time, vaccineIntake, note, cared;
    EditText et_vpetname, et_vdate, et_vtime, et_cared, et_vaccineintake, et_vnote;
    private FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase database;
    private Context context;
    DatabaseReference reference;
    ArrayList<PetVaccineFirebase> vaccineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_detail);

        et_vpetname = findViewById(R.id.et_vpetName);
        et_vdate = findViewById(R.id.et_vdate);
        et_vtime = findViewById(R.id.et_vtime);
        et_vaccineintake = findViewById(R.id.et_vaccine);
        et_cared = findViewById(R.id.et_cared);
        et_vnote = findViewById(R.id.et_vnotes);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Vaccine").child(fAuth.getUid());

        Intent intent = getIntent();
        vaccineId = intent.getStringExtra("vaccineid");

        petname = intent.getStringExtra("petname");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        vaccineIntake = intent.getStringExtra("vaccineIntake");
        note = intent.getStringExtra("note");
        cared = intent.getStringExtra("cared");

        et_vpetname.setText(petname);
        et_vdate.setText(date);
        et_vtime.setText(time);
        et_vaccineintake.setText(vaccineIntake);
        et_cared.setText(cared);
        et_vnote.setText(note);

    }
}