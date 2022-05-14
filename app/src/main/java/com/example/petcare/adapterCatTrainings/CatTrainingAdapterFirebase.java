package com.example.petcare.adapterCatTrainings;

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
import com.example.petcare.CatTrainingDetailActivity;
import com.example.petcare.CatTrainingFirebase;
import com.example.petcare.DogTipsDetailActivity;
import com.example.petcare.R;

import java.util.ArrayList;

public class CatTrainingAdapterFirebase extends RecyclerView.Adapter<CatTrainingAdapterFirebase.ViewHolder>{
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<CatTrainingFirebase> catTrainingList;

    public CatTrainingAdapterFirebase(Context mContext, ArrayList<CatTrainingFirebase> catTrainingList) {
        this.mContext = mContext;
        this.catTrainingList = catTrainingList;
    }

    @NonNull
    @Override
    public CatTrainingAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_cat_training,parent,false);

        return new CatTrainingAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatTrainingAdapterFirebase.ViewHolder holder, int position) {
        //get data
        CatTrainingFirebase catTrainingFirebase = catTrainingList.get(position);
        final String title = catTrainingFirebase.getTitle();
        final String description = catTrainingFirebase.getDescription();
        String image = catTrainingFirebase.getImage();
        String video = catTrainingFirebase.getVideo();
        String link = catTrainingFirebase.getLinkUrl();
        final String uid = catTrainingFirebase.getTrainingId();

        //set data
        holder.tv_cat_training.setText(title);
        Glide.with(mContext)
                .load(catTrainingList.get(position).getImage())
                .into(holder.img_cat_training);

    }

    @Override
    public int getItemCount() {
        return catTrainingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_cat_training;
        TextView tv_cat_training;
        TextView description;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            cardView = itemView.findViewById(R.id.card_container);
            img_cat_training = itemView.findViewById(R.id.image_cat_training);
            tv_cat_training = itemView.findViewById(R.id.tv_cat_training);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Training: " + catTrainingList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), CatTrainingDetailActivity.class);
            intent.putExtra("trainingName", catTrainingList.get(getAdapterPosition()).getTitle());
            intent.putExtra("trainingDetail", catTrainingList.get(getAdapterPosition()).getDescription());
//            intent.putExtra("image", catTrainingList.get(getAdapterPosition()).getImage());
            intent.putExtra("video", catTrainingList.get(getAdapterPosition()).getVideo());
            intent.putExtra("link", catTrainingList.get(getAdapterPosition()).getLinkUrl());
            intent.putExtra("trainingId", catTrainingList.get(getAdapterPosition()).getTrainingId());
            view.getContext().startActivity(intent);

        }
    }
}
