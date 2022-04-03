package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        btnRegister = findViewById(R.id.btn_logout);
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
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String retype = etRetype.getText().toString();
        String petname = etPetname.getText().toString();
        String petage = etPetage.getText().toString();
        String petgender = spn_type.getSelectedItem().toString();

        if(name.isEmpty()){
            etName.setError("Username is required!");
            etName.requestFocus();
        }

        else if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide valid email!");
            etEmail.requestFocus();
        }

        else if(password.isEmpty()){
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
        }

        else if(password.length()<6){
            etPassword.setError("Password must be more than 6 characters!");
            etPassword.requestFocus();
        }

        else if(retype.isEmpty()){
            etRetype.setError("Re-type password is required!");
            etRetype.requestFocus();
        }

        else if(!password.equals(retype)){
            etRetype.setError("Password not matched!");
            etRetype.requestFocus();

        }

        else if(petname.isEmpty()){
            etPetname.setError("Pet name is required!");
            etPetname.requestFocus();
        }

        else if(petage.isEmpty()){
            etPetage.setError("Pet age is required!");
            etPetage.requestFocus();
        }

/*        else if(petgender.isEmpty()){
            spn_type.set("Start recovery date is required!");
            spn_type.requestFocus();
        }
*/

        else{
            progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        sendUserToNextActivity();
                        Toast.makeText(Registration.this, "You have been registered successfully!",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(Registration.this, "Failed to register! Try again!",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                private void sendUserToNextActivity() {
                    Intent intent = new Intent(Registration.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

        }

    }

    private void sendUserData() {
        /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();*/
        String uid = fAuth.getUid();
//        reference = FirebaseDatabase.getInstance().getReference(fAuth.getUid());

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String petname = etPetname.getText().toString();
        String petage = etPetage.getText().toString();
        String petgender = spn_type.getSelectedItem().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("password", password);
        hashMap.put("petname", petname);
        hashMap.put("petage", petage);
        hashMap.put("petgender", petgender);

        reference = FirebaseDatabase.getInstance().getReference("UserData");
//        UserData UserData = new UserData(name, password, email, age, startDate, endDate);
        reference.child(uid).setValue(hashMap);
//        reference.setValue(UserData);

    }
}