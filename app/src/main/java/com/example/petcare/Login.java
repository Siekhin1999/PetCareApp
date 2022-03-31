package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView btn_reg;
    EditText et_email,et_password;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_reg = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btnlogin);
        et_email = findViewById(R.id.et_petage);
        et_password = findViewById(R.id.et_password);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void perforLogin() {

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();


        if(email.isEmpty()){
            et_email.setError("Email is required!");
            et_email.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please provide valid email!");
            et_email.requestFocus();
        }

        else if(password.isEmpty()){
            et_password.setError("Password is required!");
            et_password.requestFocus();
        }

        else if(password.length()<6){
            et_password.setError("Password must be more than 6 characters!");
            et_password.requestFocus();
        }




        else{
            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendUserToNextActivity();
                        Toast.makeText(Login.this, "Login successfully!",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(Login.this, "Failed to Login! Try again!",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                private void sendUserToNextActivity() {
                    Intent intent = new Intent(Login.this, Homepage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }




}