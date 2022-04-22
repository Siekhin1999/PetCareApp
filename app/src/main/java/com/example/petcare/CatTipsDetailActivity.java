package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class CatTipsDetailActivity extends AppCompatActivity {

    String tipsId="";
    private FirebaseAuth fAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_tips_detail);

        Intent intent = getIntent();
        tipsId = intent.getStringExtra("tipsId");

        TextView tvTitle = findViewById(R.id.img_title);
        ImageView img = findViewById(R.id.img_Detail);
        TextView tvDescription = findViewById(R.id.img_Desc);

        tvTitle.setText(intent.getStringExtra("tipsName"));
        tvDescription.setText(intent.getStringExtra("tipsDetail"));
//        img.setImageResource(getIntent().getIntExtra("image" ,0));
        String image = intent.getStringExtra("image");
        Glide.with(CatTipsDetailActivity.this).load(image).into(img);


    }
}