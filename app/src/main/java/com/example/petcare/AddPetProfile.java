package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AddPetProfile extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView imageView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_profile);


    }
}