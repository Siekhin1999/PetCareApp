package com.example.petcare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class DogTrainingDetailActivity extends AppCompatActivity {

    String trainingId="";
    private FirebaseAuth fAuth;
    private Context context;
    VideoView videoView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_training_detail);

        Intent intent = getIntent();
        trainingId = intent.getStringExtra("trainingId");

        TextView tvTitle = findViewById(R.id.video_title);
        TextView tvDescription = findViewById(R.id.video_Desc);
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_youtube);

        MediaController mediaController = new MediaController(this);
        videoView = findViewById(R.id.video_Detail);
        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);


        tvTitle.setText(intent.getStringExtra("trainingName"));
        tvDescription.setText(intent.getStringExtra("trainingDetail"));
        String video = intent.getStringExtra("video");
        Uri uri = Uri.parse(video);
        videoView.setVideoURI(uri);
        videoView.start();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = intent.getStringExtra("link");
                Uri uri1 = Uri.parse(link);
                startActivity(new Intent(Intent.ACTION_VIEW,uri1));

            }
        });

    }
}
