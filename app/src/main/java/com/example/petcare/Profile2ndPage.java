package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile2ndPage extends AppCompatActivity {

    TextView tvpetname, tvpetage, tvgender, tvusername, tvemail;
    CardView btnchangephoto;
    ImageView imageView;
    Button btnlogout;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase database;
    DatabaseReference userRef;
    private static final String USERS = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2nd_page);



        tvpetname = findViewById(R.id.tv_petname);
        tvpetage = findViewById(R.id.tv_petage);
        tvgender = findViewById(R.id.tv_gender);
        tvusername = findViewById(R.id.tv_petowner);
        tvemail = findViewById(R.id.tv_email);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("UserData");

        //set onclickliner on imageview to open uploadprofileactivity
        btnchangephoto = findViewById(R.id.btn_changephoto);
        imageView = findViewById(R.id.img_pet3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile2ndPage.this, UploadProfilePicActivity.class);
                startActivity(intent);
            }
        });

        btnlogout = findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile2ndPage.this, "You've Successfully Logout!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile2ndPage.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //show user information
        userRef.child(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //successfully

                tvpetname.setText("Pet Name: " + dataSnapshot.child("petname").getValue());
                tvpetage.setText("Pet Age: " + dataSnapshot.child("petage").getValue());
                tvgender.setText("Gender: " + dataSnapshot.child("petgender").getValue());
                tvusername.setText("Pet Owner: " + dataSnapshot.child("name").getValue());
                tvemail.setText("Email: " + dataSnapshot.child("email").getValue());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile2ndPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}