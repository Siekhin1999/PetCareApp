package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Diary2ndPage extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView petDiaryRecycler;
    ArrayList<PetDiaryFirebase> diarylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary2nd_page);

//        TextView tv_title = findViewById(R.id.tv_petdiary);
//        petDiaryRecycler = findViewById(R.id.petDiaryRecycler);
//        FloatingActionButton floatingActionButton = findViewById(R.id.btn_adddiary);

//        reference = FirebaseDatabase.getInstance().getReference();

        //for pet diary recyclerview
//        diarylist = new ArrayList<>();
//        GetDiaryDataFromFirebase();

//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
///            public void onClick(View view) {
//                Intent intent = new Intent(Diary2ndPage.this, Diary3rdPage.class);
//                startActivity(intent);
//            }
//        });
    }

    private void GetDiaryDataFromFirebase() {


    }
}