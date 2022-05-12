package com.example.petcare.adapterDogTrainings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcare.DogTipsDetailActivity;
import com.example.petcare.DogTrainingFirebase;
import com.example.petcare.R;

import java.util.ArrayList;

public class DogTrainingAdapterFirebase extends RecyclerView.Adapter<DogTrainingAdapterFirebase.ViewHolder>{
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<DogTrainingFirebase> dogTrainingList;

    public DogTrainingAdapterFirebase(Context mContext, ArrayList<DogTrainingFirebase> dogTrainingList) {
        this.mContext = mContext;
        this.dogTrainingList = dogTrainingList;
    }

    @NonNull
    @Override
    public DogTrainingAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_dog_training,parent,false);

        return new DogTrainingAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogTrainingAdapterFirebase.ViewHolder holder, int position) {
        //get data
        DogTrainingFirebase dogTrainingFirebase = dogTrainingList.get(position);
        final String title = dogTrainingFirebase.getTitle();
        final String description = dogTrainingFirebase.getDescription();
        String image = dogTrainingFirebase.getImage();
        String video = dogTrainingFirebase.getVideo();
        String link = dogTrainingFirebase.getLinkUrl();
        final String uid = dogTrainingFirebase.getTrainingId();

        //set data
        holder.tv_dog_training.setText(title);
        Glide.with(mContext)
                .load(dogTrainingList.get(position).getImage())
                .into(holder.img_dog_training);

    }

    @Override
    public int getItemCount() {
        return dogTrainingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_dog_training;
        TextView tv_dog_training;
        TextView description;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            img_dog_training = itemView.findViewById(R.id.image_dog_training);
            tv_dog_training = itemView.findViewById(R.id.tv_dog_training);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Training: " + dogTrainingList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), DogTipsDetailActivity.class);
            intent.putExtra("tipsName", dogTrainingList.get(getAdapterPosition()).getTitle());
            intent.putExtra("tipsDetail", dogTrainingList.get(getAdapterPosition()).getDescription());
            intent.putExtra("image", dogTrainingList.get(getAdapterPosition()).getImage());
            intent.putExtra("video", dogTrainingList.get(getAdapterPosition()).getVideo());
            intent.putExtra("link", dogTrainingList.get(getAdapterPosition()).getLinkUrl());
            intent.putExtra("tipsId", dogTrainingList.get(getAdapterPosition()).getTrainingId());
            view.getContext().startActivity(intent);

        }
    }
}
