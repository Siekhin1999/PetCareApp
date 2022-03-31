package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    Button btnRegister;
    EditText etName, etEmail, etPassword, etRetype, etPetname, etPetage;
    Spinner spn_type;
    TextView backLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegister = findViewById(R.id.btn_register);
        etName = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRetype = findViewById(R.id.etcpass);
        etPetname = findViewById(R.id.et_petname);
        etPetage = findViewById(R.id.et_petage);
        spn_type = findViewById(R.id.spn_type);
        backLogin = findViewById(R.id.backLogin);
        progressBar = findViewById(R.id.progressBar);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {


    }
}