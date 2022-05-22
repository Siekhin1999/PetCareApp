package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class AddPetProfile extends AppCompatActivity {

    ProgressBar progressBar;
    EditText et_apetName, et_apetage;
    Button btn_add;
    String name, email, password, image;
    Spinner spn_addtype;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseStorage fStorage;
    DataSnapshot dataSnapshot;
    DatabaseReference reference, nameref, emailref, passref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_profile);

        et_apetName = findViewById(R.id.et_apetName);
        et_apetage = findViewById(R.id.et_apet_age);
        spn_addtype = findViewById(R.id.spn_addtype);
        btn_add = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("UserData");

//        fUser = fAuth.getCurrentUser();
//        fStorage = FirebaseStorage.getInstance();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                reference = FirebaseDatabase.getInstance().getReference("UserData");
//                if (reference.child(fAuth.getUid()).orderByKey()<=2)
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String apetName = et_apetName.getText().toString();
        String spetAge = et_apetage.getText().toString();
        String aType = spn_addtype.getSelectedItem().toString();

        if(apetName.isEmpty()){
            et_apetName.setError("Pet Name is required!");
            et_apetName.requestFocus();
        }

        else if(spetAge.isEmpty()){
            et_apetage.setError("Email is required!");
            et_apetage.requestFocus();
        }
        else {
            saveNewPetData();
        }
    }

    private void saveNewPetData() {
        progressBar.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference("UserData");
        nameref = FirebaseDatabase.getInstance().getReference("UserData").child(fAuth.getUid());
        emailref = FirebaseDatabase.getInstance().getReference("UserData").child(fAuth.getUid());
        passref = FirebaseDatabase.getInstance().getReference("UserData").child(fAuth.getUid());

        String newPetId = reference.push().getKey();
        String uid = fAuth.getUid();
        String apetName = et_apetName.getText().toString();
        String apetAge = et_apetage.getText().toString();
        String apetType = spn_addtype.getSelectedItem().toString();

//        String name = reference.child(fAuth.getUid()).child("name").getKey();
//        String name = dataSnapshot.child("name").getValue().toString();


//        String email = reference.child(fAuth.getUid()).child("email").getKey();
//        String email = dataSnapshot.child("email").getValue().toString();
//        String password = reference.child(fAuth.getUid()).child("password").getKey();
//        String imageDog = fStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_dog.jpg?alt=media&token=74491c13-a848-4cdc-85dd-39de10c393d9").getDownloadUrl().toString();
//        String imageCat = fStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/pet-care-application-fc562.appspot.com/o/avatar_cat.jpg?alt=media&token=133f7529-89bd-4fd9-aba2-77542a724d1a").getDownloadUrl().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);

        nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = (String) ds.child("name").getValue();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emailref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = (String) ds.child("email").getValue();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        passref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String password = (String) ds.child("password").getValue();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("password", password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hashMap.put("newPetname", apetName);
        hashMap.put("newPetage", apetAge);
        hashMap.put("newPetType", apetType);


        if (spn_addtype.getSelectedItem().equals("Male, Dog") || spn_addtype.getSelectedItem().equals("Female, Dog") ){
            image = "avatar_dog.jpg";
        }
        else if (spn_addtype.getSelectedItem().equals("Male, Cat")|| spn_addtype.getSelectedItem().equals("Female, Cat")){
            image = "avatar_cat.jpg";
        }

        FirebaseStorage.getInstance().getReference(image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                hashMap.put("image",  uri.toString());
                reference.child(fAuth.getUid()).child("AddedPet").child(newPetId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            et_apetName.getText().clear();
                            et_apetage.getText().clear();
                            //spn_addtype.getSelectedItem().toString();

                            Toast.makeText(AddPetProfile.this, "New Pet Added!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                        else {
                            Toast.makeText(AddPetProfile.this, "Failed Added", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }
}