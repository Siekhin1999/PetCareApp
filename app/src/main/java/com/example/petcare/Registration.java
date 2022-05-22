package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    Button btnRegister;
    EditText etName, etEmail, etPassword, etRetype, etPetname, etPetage;
    Spinner spn_type;
    TextView backLogin;
//    String imgDog="", imgCat="";
    String image;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
//    FirebaseUser fUser;
//    FirebaseDatabase rootNode;
//    FirebaseStorage fStorage;
    DatabaseReference reference;
//    StorageReference storageReference, storageReference2;

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
        reference = FirebaseDatabase.getInstance().getReference("UserData");

//        fUser = fAuth.getCurrentUser();
//        fStorage = FirebaseStorage.getInstance();

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
//        String profileid = reference.push().getKey();
        String uid = fAuth.getUid();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String petname = etPetname.getText().toString();
        String petage = etPetage.getText().toString();
        String petgender = spn_type.getSelectedItem().toString();
//        String imageDog = fStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_dog.jpg?alt=media&token=74491c13-a848-4cdc-85dd-39de10c393d9").getDownloadUrl().toString();
//        String imageCat = fStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_cat.jpg?alt=media&token=133f7529-89bd-4fd9-aba2-77542a724d1a").getDownloadUrl().toString();

//        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_dog.jpg?alt=media&token=74491c13-a848-4cdc-85dd-39de10c393d9");
//        storageReference2 = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_cat.jpg?alt=media&token=133f7529-89bd-4fd9-aba2-77542a724d1a");

//        String imageDog = storageReference.child("avatar_dog.jpg").getDownloadUrl().toString();
//        String imageCat = storageReference2.child("avatar_cat.jpg").getDownloadUrl().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("password", password);
        hashMap.put("petname", petname);
        hashMap.put("petage", petage);
        hashMap.put("petgender", petgender);

//        Task<Uri> imageDog = storageReference2.child("avatar_dog.jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()){
//                    Uri uri = task.getResult();
//                    imgDog = uri.toString();
//                }
//            }
//        });

//        Task<Uri> imageCat = storageReference2.child("avatar_cat.jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()){
//                    Uri uri1 = task.getResult();
//                    imgCat = uri1.toString();
//                }
//            }
//        });

        if (spn_type.getSelectedItem().equals("Male, Dog") || spn_type.getSelectedItem().equals("Female, Dog") ){
            image = "avatar_dog.jpg";
//            hashMap.put("image",  FirebaseStorage.getInstance().getReferenceFromUrl("avatar_dog.jpg"));
        }
        else if (spn_type.getSelectedItem().equals("Male, Cat")|| spn_type.getSelectedItem().equals("Female, Cat")){
            image = "avatar_cat.jpg";
//            hashMap.put("image",  FirebaseStorage.getInstance().getReferenceFromUrl("avatar_cat.jpg"));
        }

        FirebaseStorage.getInstance().getReference(image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                hashMap.put("image",  uri.toString());
                reference.child(uid).setValue(hashMap);
            }
        });

//        reference = FirebaseDatabase.getInstance().getReference("UserData");

//        reference.child(uid).setValue(hashMap);

    }
}